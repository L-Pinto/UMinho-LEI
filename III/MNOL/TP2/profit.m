function [f] = profit(x, a)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
R = a(1)*x(1)+a(2)*x(2)+a(3)*x(3); % revenue
C = x(1)^2+3*x(1)^2*x(2)*x(3)+x(2)^2+x(3)^2; % cost
P = R - C; % profit
f = -P; % Para prob de maximizacao
end
% a = [2 3 4];
% options = optimset('MaxFunEvals', 3000000, 'MaxIter', 3000000)
%[xmin,fmin,exitflag,output]=fminunc('profit',[1 2 1],[],a)
%[xmin,fmin,exitflag,output]=fminsearch('profit',[1 2 1],[],a)