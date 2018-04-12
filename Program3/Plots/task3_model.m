filename = 'outputNMOS.txt';
A = importdata(filename);
len = length(A.data);

%plot of task3 
%plots of ID vs. VDS with the 10 values of VGS for IDmodel. 
ref = [0.5,1,1.5,2,2.5,3,3.5,4,4.5,5];
%initial
% Vt = 0.026;
% k = 1;
% Is = power(10, -7);
% Vth = 1;
% Vsb = 0;
% Id = (len);

Vt = 0.026;
k = 0.9999999999999997;
Is = 9.999999999999996 * power(10, -8);
Vth = 1.000000000000001;
Vsb = 0;
Id = (len);

%obtain Id
for i = 1:len
    Vgs = A.data(i, 1);
    Vds = A.data(i, 2);
    Ids = A.data(i, 3);
    
    Vgb = Vgs;
    Vdb = Vds;
    
    Id(i) = cal_Id(Is, k, Vgb, Vth, Vt, Vsb, Vdb);
end

len2 = length(Id);

%process data 
begin = 0; 
final = 0;
for i = 1:10
    for j = begin+1:1000
        if A.data(j,1) == ref(i)
            final = j;
        else
            break;
        end
    end
    
    %Vgs = A.data(begin+1:final, 1);
    Vds_part = A.data(begin+1:final, 2);
    %Ids = A.data(begin+1:final, 3);
    Id_part = Id(begin+1:final);
      
    legend('-DynamicLegend');
    %plot(Vds, Ids,'.-','DisplayName', sprintf('Vgs = %1.1f', ref(i)))
    plot(Vds_part, Id_part,'.-','DisplayName', sprintf('Vgs = %1.1f', ref(i))) 
    xlabel('Vds(V)')
    ylabel('Model Id(A)')
    title("Task3: Vds vs. Model Id for given Vgs")
    hold on;
    
    begin = final+1; 
end

%ylim([0,5*power(10,-3)]);