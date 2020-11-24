// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    // ----------*********-----------

    // SOME PRIVATE HELPER FUNCTIONS

    private boolean isHeadSentinel()  // OVERLOADED function isHeadSentinel()
    {
        if(this.parent==null)
            return true;
        return false;
    }

    private boolean isHeadSentinel(BSTree node)
    {
        if(node.parent==null)
            return true;
        return false;
    }

    private BSTree getHeadSentinel()
    {
        BSTree curr=this;
        while(curr.parent!=null)
            curr=curr.parent;
        return curr;
    }

    private BSTree getActualRoot()
    {
       BSTree headSentinel=this.getHeadSentinel();
       if(headSentinel.right==null){
        return null;
       }
       return headSentinel.right;
    }

    private boolean isActualRoot()
    {
        BSTree curr=this.getActualRoot();
        return (curr==this);
    }

    private BSTree getLowestNodeInTreeWithGivenRoot()
    {
        BSTree curr=this;
        if(curr==null)
            return null;
        while(curr.left!=null)
            curr=curr.left;
        return curr;
    }

    private BSTree getLargestNodeInTreeWithGivenRoot()
    {
        BSTree curr=this;
        if(curr==null)
            return null;
        while(curr.right!=null)
            curr=curr.right;
        return curr;
    }

    private BSTree getParent()
    {
        if(this.isHeadSentinel())
            return null;
        return this.parent;
    }

    private int compareTo(Dictionary d)
    {
        if(this.key>d.key)
            return 1;
        if(this.key<d.key)
            return -1;
        // this.key==d.key

        if(this.address>d.address)
            return 1;
        if(this.address<d.address)
            return -1;
        return 0; // key and address both same
    }

    private void removeNode()
    {
        BSTree curr=this;
        // this node has to be deleted
        if(curr.left==null && curr.right==null) // case 1 leaf node
        {
            // System.out.println("leaf node to be deleted");
            if(curr==curr.parent.left)
                curr.parent.left=null;
            else
                curr.parent.right=null;

            // System.out.println("Deleted");

            return ;

            // return true;
        }

        else if(curr.left==null && curr.right!=null) // case 2.1 left child null, right child present
        {
            // System.out.println("Node to be printed has only RIGHT child");
            if(curr.parent.left==curr)
            {
                curr.parent.left=curr.right;
                curr.right.parent=curr.parent;
            }
            else
            {
                curr.parent.right=curr.right;
                curr.right.parent=curr.parent;
            }

            // System.out.println("Deleted");
            return ;

            // return true;
        }

        else if(curr.left!=null && curr.right==null) // case 2.2 right child null, left child present
        {
            // System.out.println("Node to be printed has only LEFT child");
            if(curr.parent.left==curr)
            {
                curr.parent.left=curr.left;
                curr.left.parent=curr.parent;
            }
            else
            {
                curr.parent.right=curr.left;
                curr.left.parent=curr.parent;
            }
            // System.out.println("Deleted");
            // return true;
            return ;
        }
        else // case 3 both children present
        {
            // System.out.println("Node to be printed is having 2 children");
            // System.out.println(curr.right.key);
            // System.out.println(curr.left.key);
            // System.out.println("key to be deleted=" + this.key);
            // System.out.println("ADDRESS to be deleted=" + this.address);
            // System.out.println("SIZE to be deleted=" + this.size);
            // BSTree successor=curr.right.getLowestNodeInTreeWithGivenRoot();
            BSTree successor;
            successor=curr.right;
            while(successor.left!=null)
                successor=successor.left;
            // System.out.println("Sucessor's key=" + successor.key);
            // System.out.println("Successor's address=" + successor.address);
            // System.out.println("Sucessor's size=" + successor.size);
            int add=successor.address;
            int k=successor.key;
            int s=successor.size;
            Dictionary temp=(Dictionary)successor;
            curr.Delete(temp);
            curr.key=k;
            curr.address=add;
            curr.size=s;
            // System.out.println("deleted");
            // return true;
            return ;
        }
            
    }

    // -------------******--------------

    // NOW COMING TO THE MAIN FUNCTIONS

    public BSTree Insert(int address, int size, int key) // made changes
    { 
        BSTree new_node=new BSTree(address,size,key);
        BSTree curr=this.getActualRoot();
        // initially no node present, only head sentinel
        if(curr==null)
        {
            BSTree rootSentinel=this.getHeadSentinel();
            rootSentinel.right=new_node;
            new_node.parent=rootSentinel;
            return new_node;
        }
        BSTree currParent=curr.parent;
        // while(curr.parent!=null)
        //     curr=curr.parent;
        // curr=curr.right;
        // curr is now the root node
        while(curr!=null)
        {
            if(key>curr.key)
            {
                currParent=curr;
                curr=curr.right;
            }
            else if(key<curr.key)
            {
                currParent=curr;
                curr=curr.left;
            }
            else // key=curr.key
            {
                if(address>curr.address)
                {
                    currParent=curr;
                    curr=curr.right;
                }
                else
                {
                    currParent=curr;
                    curr=curr.left;
                }
            }
        }
      
        if(key<currParent.key)
        {
            currParent.left=new_node;
            new_node.parent=currParent;
            return new_node;
            
        }
        if(key>currParent.key)
        {
            currParent.right=new_node;
            new_node.parent=currParent;
            return new_node;
        }
        // if(address==currParent.address)
        //     return currParent;
        if(address<currParent.address)
        {
            currParent.left=new_node;
            new_node.parent=currParent;
            return new_node;
        }
        currParent.right=new_node;
        new_node.parent=currParent;
        return new_node;

    }    

    public boolean Delete(Dictionary e) // seems okayish, might have errors CHANGES
    { 
        BSTree curr=this.getActualRoot();
        if(curr==null)
            return false;
        BSTree currParent=curr.parent;
        while(curr!=null)
        {
            if(curr.compareTo(e)>0) // curr ki key/address is greater than e key => go left
            {
                currParent=curr;
                curr=curr.left;
            }
            else if(curr.compareTo(e)<0)
            {
                currParent=curr;
                curr=curr.right;
            }
            else
            {
                if(curr.size==e.size)
                {
                    curr.removeNode();
                    return true;
                }
                else
                    return false;

            }
        }
        return false;
        
    }
        
    public BSTree Find(int key, boolean exact) // updation required
    { 
        BSTree curr=this.getFirst();
        if(curr==null)
            return null;
        if(exact)
        {
            while(curr!=null)
            {
                if(curr.key==key)
                    return curr;
                curr=curr.getNext();
            }
            return null;
        }
        while(curr!=null)
        {
            if(curr.key >= key)
                return curr;
            curr=curr.getNext();
        }
        return null;
        
    }

    public BSTree getFirst() // made changes 
    { 
        BSTree actualRoot=this.getActualRoot();
        if(actualRoot==null)
            return null;
        // actual root is not null
        if(actualRoot.left==null)
            return actualRoot;
        BSTree curr = actualRoot.left.getLowestNodeInTreeWithGivenRoot();
        return curr;

    }

    public BSTree getNext() // made changes
    { 
        BSTree curr=this;
        // if(curr==null)
        //     return null;
        if(curr.right!=null)
        {
            curr=curr.right;
            BSTree succ=curr.getLowestNodeInTreeWithGivenRoot();
            // if(this.key==succ.key && this.address==succ.address)
            //     return succ.getNext();
            return succ;
        }
        BSTree currParent = curr.parent;
        while((!currParent.isHeadSentinel()) && curr==currParent.right)
        {
            curr=currParent;
            currParent=currParent.parent;
        }
        if(currParent.isHeadSentinel())
            return null;
        if(this.key==currParent.key && this.address==currParent.address)
            return currParent.getNext();
        return currParent;



    }

    public boolean sanity()
    { 
        BSTree curr=this;
        BSTree slow,fast;
        if()
        return true;
    }

    // public static void main(String[] args)
    // {
    //     BSTree bst = new BSTree();
    //     bst.Insert(9000,1000,1000);
    //     bst.Insert(0,9000,9000);


    //     System.out.println(bst.getFirst().address);
    // }

}


 


