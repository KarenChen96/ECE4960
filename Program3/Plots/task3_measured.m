filename = 'outputNMOS.txt';
A = importdata(filename);

%plot of task3
%plots of ID vs. VDS with the 10 values of VGS for IDmeasured. 

ref = [0.5,1,1.5,2,2.5,3,3.5,4,4.5,5]; 
%process data 
begin = 0; 
final = 0;

for i = 1:10
    for k = begin+1:1000
        if A.data(k,1) == ref(i)
            final = k;
        else
            break;
        end
    end

    Vds = A.data(begin+1:final, 2);
    Ids_Measured = A.data(begin+1:final, 3);
    
    legend('-DynamicLegend');
    plot(Vds, Ids_Measured,'.-','DisplayName', sprintf('Vgs = %1.1f', ref(i))) 
    xlabel('Vds(V)')
    ylabel('Measured Id(A)')
    title("Task3: Vds vs. Measured Id for given Vgs")
    hold on;
    
    begin = final+1; 
   
end

%ylim([0,5*power(10,-3)]);