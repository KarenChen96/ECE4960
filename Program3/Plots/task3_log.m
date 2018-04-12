filename = 'outputNMOS.txt';
A = importdata(filename);

%plot of task3 -- plots of log(ID) vs. VGS with two VDS for both IDmeasured and IDmodel.  
ref = [0.5,1,1.5,2,2.5,3,3.5,4,4.5,5];
%process data 
begin1 = 0; 
final1 = 0;
begin2 = 0; 
final2 = 0;

for k = 1:1000
    if A.data(k,1) == 1
        begin1 = k;
        break;
    end
end
    
for k = begin1:1000
    if A.data(k,1) == 1
        final1 = k;
    else
        break;
    end
end

for k = 1:1000
    if A.data(k,1) == 2
        begin2 = k;
        break;
    end
end

for k = begin2:1000
    if A.data(k,1) == 2
        final2 = k;
    else
        break;
    end
end
    
    Vgs1 = A.data(begin1:final1, 1);
    Vds1 = A.data(begin1:final1, 2);
    Ids_Measured1 = A.data(begin1:final1, 3);
    log_Measured1 = log(Ids_Measured1);
    
    Vgs2 = A.data(begin2:final2, 1);
    Vds2 = A.data(begin2:final2, 2);
    Ids_Measured2 = A.data(begin2:final2, 3);
    log_Measured2 = log(Ids_Measured2);
   
    plot(Vgs1, log_Measured1, '.-',Vgs2, log_Measured2, '.-' ) 
    legend('Vgs = 1','Vgs = 2')
    xlabel('Vds(V)')
    ylabel('Measured log(Id)(A)')
    title("Task3: Vds vs. log(Id) (Measured) for given Vgs")
   
ylim([-30,-4]);