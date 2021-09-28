%% dados
A = [ 1880
1881
1882
1883
1884
1885
1886
1887
1888
1889
1890
1891
1892
1893
1894
1895
1896
1897
1898
1899
1900
1901
1902
1903
1904
1905
1906
1907
1908
1909
1910
1911
1912
1913
1914
1915
1916
1917
1918
1919
1920
1921
1922
1923
1924
1925
1926
1927
1928
1929
1930
1931
1932
1933
1934
1935
1936
1937
1938
1939
1940
1941
1942
1943
1944
1945
1946
1947
1948
1949
1950
1951
1952
1953
1954
1955
1956
1957
1958
1959
1960
1961
1962
1963
1964
1965
1966
1967
1968
1969
1970
1971
1972
1973
1974
1975
1976
1977
1978
1979
1980
1981
1982
1983
1984
1985
1986
1987
1988
1989
1990
1991
1992
1993
1994
1995
1996
1997
1998
1999
2000
2001
2002
2003
2004
2005
2006
2007
2008
2009
2010
2011
2012
2013
2014
2015
2016
2017
2018
2019
]; 

B = [
 -0.16
 -0.07
 -0.10
 -0.16
 -0.27
 -0.33
 -0.31
 -0.35
 -0.17
 -0.10
 -0.35
 -0.22
 -0.27
 -0.31
 -0.30
 -0.23
 -0.11
 -0.11
 -0.27
 -0.18
 -0.08
 -0.15
 -0.27
 -0.36
 -0.46
 -0.26
 -0.22
 -0.38
 -0.42
 -0.48
 -0.43
 -0.44
 -0.36
 -0.34
 -0.15
 -0.14
 -0.35
 -0.46
 -0.29
 -0.27
 -0.27
 -0.19
 -0.28
 -0.26
 -0.27
 -0.22
 -0.10
 -0.22
 -0.20
 -0.36
 -0.16
 -0.10
 -0.16
 -0.29
 -0.13
 -0.20
 -0.15
 -0.03
  0.00
 -0.02
  0.13
  0.19
  0.07
  0.09
  0.20
  0.09
 -0.07
 -0.03
 -0.11
 -0.11
 -0.17
 -0.07
  0.01
  0.08
 -0.13
 -0.14
 -0.19
  0.05
  0.06
  0.03
 -0.02
  0.06
  0.03
  0.05
 -0.20
 -0.11
 -0.06
 -0.02
 -0.08
  0.05
  0.03
 -0.08
  0.01
  0.16
 -0.07
 -0.01
 -0.10
  0.18
  0.07
  0.16
  0.26
  0.32
  0.14
  0.31
  0.16
  0.12
  0.18
  0.32
  0.39
  0.27
  0.45
  0.41
  0.22
  0.23
  0.32
  0.45
  0.33
  0.46
  0.61
  0.38
  0.39
  0.54
  0.63
  0.62
  0.54
  0.68
  0.64
  0.67
  0.54
  0.66
  0.72
  0.61
  0.64
  0.68
  0.75
  0.90
  1.01
  0.92
  0.85
  0.98
] ;

%% modelo polinomial 
[p1,s1] = polyfit(A,B,1) %grau 1
[p2,s2] = polyfit(A,B,2) %grau 2
[p3,s3] = polyfit(A,B,3) %grau 3

%resíduos
SQR1=s1.normr^2 
SQR2=s2.normr^2
SQR3=s3.normr^2

plot(A,B,'ok') %representacao dos pontos
hold on
A_data=1880:0.1:2080;

p1_data=polyval(p1,A_data);
p2_data=polyval(p2,A_data);
p3_data=polyval(p3,A_data);

p1_2060 = polyval(p1,2060)
p2_2060 = polyval(p2,2060)
p3_2060 = polyval(p3,2060)

plot(A_data,p1_data,'b',A_data,p2_data,'g',A_data,p3_data,'r')   %representacao dos polinomios 
plot(2060,p1_2060,'db',2060,p2_2060,'dg',2060,p3_2060,'dr')

%% modelo não polinomial
[coef1,resnorm1] = lsqcurvefit(@modelo1,[1,1,1],A,B)
[coef2,resnorm2] = lsqcurvefit(@modelo2,[1,1,1],A,B)
[coef3,resnorm3] = lsqcurvefit(@modelo3,[1,1,1],A,B)
[coef4,resnorm4] = lsqcurvefit(@modelo4,[1,1,1,1],A,B)

m1_2060 = modelo1(coef1,2060)
m1_data=modelo1(coef1,A_data);

m2_2060 = modelo2(coef2,2060)
m2_data=modelo2(coef2,A_data);

m3_2060 = modelo3(coef3,2060)
m3_data=modelo3(coef3,A_data);

m4_2060 = modelo4(coef4,2060)
m4_data=modelo4(coef4,A_data);

plot(A,B,'ok',A_data,m1_data,'g',A_data,m2_data,'b',A_data,m3_data,'m',A_data,m4_data,'r')
hold on 
plot(2060,m1_2060,'dg',2060,m2_2060,'db',2060,m3_2060,'dm',2060,m4_2060,'dr')
%% para fazer a representacao grafica de tudo o final
A_data=1880:0.1:2080;
p1_data=polyval(p1,A_data);
p2_data=polyval(p2,A_data);
m1_data=modelo(coef,A_data);
plot(A,B,'ok',A_data,p1_data,'b',A_data,p2_data,'g',A_data,p3_data,'r',A_data,m1_data,'m')
