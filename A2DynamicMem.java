// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {


    private class ResizableArray
    {
        int numberOfElements, totalCapacity;
        Dictionary[] arr;

        ResizableArray()
        {
            numberOfElements=0;
            totalCapacity=1;
            arr=new Dictionary[totalCapacity];
        }

        void add(Dictionary toAdd)
        {
            if(this.numberOfElements==this.totalCapacity)
            {
                int newTotalCapacity=this.totalCapacity*2;
                Dictionary[] arr1=new Dictionary[newTotalCapacity];
                for(int i=0;i<this.totalCapacity;i++)
                    arr1[i]=this.arr[i];
                this.arr=arr1;
                this.totalCapacity=newTotalCapacity;
            }

            arr[this.numberOfElements]=toAdd;
            this.numberOfElements+=1;
        }

        int getNumberOfElements()
        {
            return this.numberOfElements;
        }

        void merge(Dictionary[] a,int start,int mid,int end) // complete merge of merge sort
        {
            int n1=mid-start+1;
            int n2=end-mid;

            Dictionary a1[]=new Dictionary[n1];
            Dictionary a2[]=new Dictionary[n2];

            for(int i=0;i<n1;i++)
                a1[i]=a[start+i];

            for(int i=0;i<n2;i++)
                a2[i]=a[mid+1+i];

            int i=0,j=0;
            int k=start;

            while(i<n1 && j<n2)
            {
                if(a1[i].address<=a2[j].address)
                {
                    a[k]=a1[i];
                    i++;
                }
                else
                {
                    a[k]=a2[j];
                    j++;
                }
                k++;
            }


            while(i<n1)
            {
                a[k]=a1[i];
                i++;
                k++;
            }
            while(j<n2)
            {
                a[k]=a2[j];
                k++;
                j++;
            }
        }

        void mergeSort(Dictionary[] a,int l,int r)
        {
            if(l<r)
            {
                int m=l+(r-l)/2;
                mergeSort(a,l,m);
                mergeSort(a,m+1,r);
                merge(a,l,m,r);
            }
        }

        void sortByStartAddress()
        {
            Dictionary[] a=this.arr;
            int l,r;
            l=0;
            r=this.getNumberOfElements() -1;
            mergeSort(a,l,r);

            return ;
        }
    }

      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment()  // TO DO- make it more efficient, insert through median

    {
        // System.out.println("Defragment started");
        // System.out.println("Initially");
        // Dictionary curr2=this.freeBlk.getFirst();
        // // System.out.println("After defrag");
        // while(curr2!=null)
        // {
        //     System.out.println("Address: " + curr2.address + " " + "Key: "+curr2.key);
        //     curr2=curr2.getNext();
        // }

        ResizableArray helperArray = new ResizableArray();
        Dictionary curr=this.freeBlk.getFirst();
        while(curr!=null)
        {
            helperArray.add(curr);
            // this.freeBlk.Delete(curr);
            curr=curr.getNext();
        }
        
        int n=helperArray.getNumberOfElements();

        // for(int i=0;i<n;i++)
        //     this.freeBlk.Delete(helperArray[i]);

        if(this.type==2)
        {
            this.freeBlk=new BSTree();
        }
        if(this.type==3)
        {
            this.freeBlk=new AVLTree();
        }

        helperArray.sortByStartAddress();

        ResizableArray h2 = new ResizableArray();

        int count=0;
        if(n==0)
            return ;
        h2.add(helperArray.arr[0]);
        int startAdress=helperArray.arr[0].address;
        int sizeOfContBlock=helperArray.arr[0].size;

        for(int i=1;i<n;i++)
        {
            if(helperArray.arr[i].address==(startAdress+sizeOfContBlock))
            {
                h2.arr[count].size+=(helperArray.arr[i].size);
                sizeOfContBlock+=(helperArray.arr[i].size);
            }
            else
            {
                count++;
                h2.add(helperArray.arr[i]);
                startAdress=helperArray.arr[i].address;
                sizeOfContBlock=helperArray.arr[i].size;
            }
        }

        int new_numberOfTotalNodes=h2.getNumberOfElements();

        if(new_numberOfTotalNodes==0)
            return ;
        
        for(int i=0;i<new_numberOfTotalNodes;i++)
        {
            this.freeBlk.Insert(h2.arr[i].address,h2.arr[i].size,h2.arr[i].size);
            
        }

        // Dictionary curr1=this.freeBlk.getFirst();
        // System.out.println("After defrag");
        // while(curr1!=null)
        // {
        //     System.out.println("Address: " + curr1.address + " " + "Key: "+curr1.key);
        //     curr1=curr1.getNext();
        // }

        // System.out.println("defragment exited");
        return ;
    }



}
