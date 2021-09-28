import java.net.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Timer;

public class IPRemover {
    Map<Map.Entry<InetAddress,Integer>,Map.Entry<Integer, Integer>> serverPool;

    private void removeElem(Map<Map.Entry<InetAddress,Integer>,Map.Entry<Integer, Integer>> serverPool) throws UnknownHostException {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        Map.Entry<InetAddress, Integer> keyMin = new AbstractMap.SimpleEntry<InetAddress, Integer>(InetAddress.getByName("129.26.70.95"),0);

        for (Map.Entry<InetAddress,Integer> p: serverPool.keySet()) {
            if(serverPool.get(p).getKey() > max){
                max = serverPool.get(p).getKey();
            }
            if(serverPool.get(p).getKey() < min){
                min = serverPool.get(p).getKey();
                keyMin = p;
            }
        }
        if(max - min >= 3) serverPool.remove(keyMin);
    }

    public static int maxValue(Map<Map.Entry<InetAddress, Integer>, Map.Entry<Integer, Integer>> serverPool){
        int max = 0;

        for (Map.Entry<InetAddress,Integer> p: serverPool.keySet()) {
            if(serverPool.get(p).getKey() > max){
                max = serverPool.get(p).getKey();
            }
        }
        return max;
    }

    public IPRemover(Map<Map.Entry<InetAddress,Integer>,Map.Entry<Integer, Integer>> serverPool) throws UnknownHostException {
        this.serverPool = serverPool;
        removeElem(serverPool);
    }
}
