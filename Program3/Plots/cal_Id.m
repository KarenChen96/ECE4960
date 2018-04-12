function Id = cal_Id(Is, k, Vgb, Vth, Vt, Vsb, Vdb)
%this function resturns the value of calculated Ids
%disp('k is' + k)
% disp(Vgb)
% disp(Vth) 
% disp(Vt) 
% disp(Vsb) 
a1 = (k * (Vgb - Vth) - Vsb) / (2*Vt);
% a2 = 1 + exp(a1);
% a3 = power(log(a2), 2);
a3 = power(log(1 + exp(a1)), 2);
a4 = Is * a3;

b1 = (k * (Vgb - Vth) - Vdb) / (2*Vt);
b2 = 1 + exp(b1);
b3 = power(log(b2), 2);
b4 = Is * b3;

Id = a4 - b4;