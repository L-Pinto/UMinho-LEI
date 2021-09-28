public class twodParityCheck {
    public static String[] encode(String[] dataWord)
    {
        int count = 0;
        char[] rRowBit = new char[dataWord.length];
        while(count<dataWord.length)
        {
            rRowBit[count] = dataWord[count].charAt(0);
            for(int i = 1 ; i < dataWord[count].length() ; i++)
            {
                if(rRowBit[count] == dataWord[count].charAt(i))
                {
                    rRowBit[count] = '0';
                }
                else
                {
                    rRowBit[count] = '1';
                }
            }
            dataWord[count] += rRowBit[count];
            count++;
        }
        String RowBit = "";
        for(int i = 0 ; i<rRowBit.length ; i++)
        {
            RowBit += rRowBit[i];
        }

        count = 0;
        char rColBit[] = new char[dataWord[0].length()];
        String ColBit = "";
        while(count<dataWord[0].length())
        {
            rColBit[count] = dataWord[0].charAt(count);
            for(int i = 1 ; i < dataWord.length ; i++)
            {
                //System.out.println(dataWord[i].charAt(count));
                if(rColBit[count] == dataWord[i].charAt(count))
                {
                    rColBit[count] = '0';
                }
                else
                {
                    rColBit[count] = '1';
                }
            }
            count++;
        }
        for(int i = 0 ; i<rColBit.length ; i++)
        {
            ColBit += rColBit[i];
        }

        String code[] = new String[dataWord.length+1];
        String codeWord = " ";

        for(int i = 0 ; i<code.length ; i++)
        {
            if( i == dataWord.length)
            {
                code[i] = ColBit;
            }
            else
            {
                code[i] = dataWord[i];
            }
        }
        for(int i = 0 ; i<code.length ; i++)
        {
            codeWord += code[i];
        }

        return code;
    }

    public static String[] decode(String[] dataWord)
    {
        String codeDecoded[] = new String[dataWord.length-1];

        for(int i = 0 ; i < dataWord.length-1 ;i++){
            codeDecoded[i] = dataWord[i].substring(0,dataWord[i].length()-1);
        }

        return codeDecoded;
    }

    public static int checkError(String[] ReCodeWord) {
        int numberOfErrors = 0;

        String lastLine = ReCodeWord[ReCodeWord.length-1];
        int lineSize = lastLine.length();

        int [] countColumn1s = new int[lineSize-1]; // last column doesnt matter
        //inicializ array
        for (int k=0; k<lineSize-1;k++){
            countColumn1s[k] = 0;
        }

        // -------------------------------------------------------- \\
        int lineErrorsCount = 0;
        int lineErrorPosition = 0;

        //Parity Bits - lines
        for (int i=0 ; i < ReCodeWord.length-1; i++) {
            String word = ReCodeWord[i];
            char rRowBit = word.charAt(word.length() - 1);

            int countLine1s = 0;
            for (int j = 0; j < word.length() - 1; j++) {
                if (word.charAt(j) == '1') {
                    countLine1s++;
                    countColumn1s[j]++;
                }
            }
            if ((countLine1s % 2 == 0 && rRowBit != '0') || (countLine1s % 2 == 1 && rRowBit != '1')){
                lineErrorsCount++;
                lineErrorPosition = i;
            }
        }

        int columnErrorsCount=0;
        int columnErrorPosition = 0;
        //match array to each column bit
        for(int l=0; l<lineSize-1;l++){
            if((countColumn1s[l]%2== 0 && lastLine.charAt(l)!='0') || (countColumn1s[l]%2== 1 && lastLine.charAt(l)!='1')) {
                columnErrorsCount++;
                columnErrorPosition = l;
            }
        }

        // -------------------------------------------------------- \\
        //correct error in position
        if (columnErrorsCount >= lineErrorsCount) numberOfErrors = columnErrorsCount ;
        else numberOfErrors = lineErrorsCount;

        if (numberOfErrors == 1){
            if (ReCodeWord[lineErrorPosition].charAt(columnErrorPosition) == '1' )
                ReCodeWord[lineErrorPosition] =
                        ReCodeWord[lineErrorPosition].substring(0,columnErrorPosition) + '0' + ReCodeWord[lineErrorPosition].substring(columnErrorPosition+1);
            else
                ReCodeWord[lineErrorPosition] =
                        ReCodeWord[lineErrorPosition].substring(0,columnErrorPosition) + '1' + ReCodeWord[lineErrorPosition].substring(columnErrorPosition+1);
        }
        /*
        for (int i=0 ; i < ReCodeWord.length; i++) {
            System.out.println(ReCodeWord[i]);
        }
        System.out.println("Number of errors: " + numberOfErrors);
        System.out.println("Line error: " + lineErrorPosition);
        System.out.println("Column error: " + columnErrorPosition);
        System.out.println("Number of line errors : " + lineErrorsCount);
        System.out.println("Number of column errors : " + columnErrorsCount);
         */
        return numberOfErrors;
    }


}
