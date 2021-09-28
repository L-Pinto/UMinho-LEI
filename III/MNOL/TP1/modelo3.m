function [ m ] = modelo3(c,x)
%c coeficientes do modelo
%x sao os dados 
m = c(1).*(exp(2*x./96-30)-50)+c(2)*1*sin(x/9)+c(3)./(log(x)*10); 
end


