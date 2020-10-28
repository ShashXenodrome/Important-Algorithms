class Node<T>
{
	T data;
	Node<T> left;
	Node<T> right;
	Node<T> parent;

	public Node(T obj)
	{
		this.data=obj;
		this.left=this.right=this.parent=null;
	}

}

public class AVLTree<T extends Comparable<T>>
{
	private Node root;
	private int currentSize;

	public AVLTree()
	{
		root=null;
		currentSize=0;
	}

	public boolean contains(T obj)
	{
		return contains(root,obj);
	}
	private boolean contains(Node<T> node, T obj)
	{
		if(node==null)
			return false;
		if(((Comparable<T>)obj).compareTo(node.data)==0)
			return true;
		if(((Comparable<T>)obj).compareTo(node.data)>0)
			return contains(node.right,obj);
		return contains(node.left,obj);
	}

	public void delete(T obj)
	{
		if(root==null)
			return ;
		if(!this.contains(obj))
			return ;
		delete(root,obj);

	}

	public Node<T> successor(Node<T> node)
	{

		Node<T> curr=node.right;
		while(curr.left!=null)
			curr=curr.left;
		return curr;
	}

	public Node<T> predecessor(Node<T> node)
	{

		Node<T> curr=node.left;
		while(curr.right!=null)
			curr=curr.right;
		return curr;
	}

	public void delete(Node<T> root,T obj)
	{
		Node<T> parent=null;
		Node<T> curr=root;

		while(curr!=null && (((Comparable<T>)obj).compareTo((T)curr.data)!=0))
		{
			parent=curr;

			if((((Comparable<T>)obj).compareTo((T)curr.data)>0))
				curr=curr.right;
			else
				curr=curr.left;
		}


		if(curr==null)
			return ;

		// case 1 leaf node


		if(curr.left==null && curr.right==null)
		{
			// System.out.println("HES");
			if(parent.left==curr)
				parent.left=null;
			else
				parent.right=null;

			checkBalance(parent);

			return ;
		}

		// case 2 only one child, subcase 1 only left child

		if(curr.left!=null&&curr.right==null)
		{
			if(curr.parent!=null)
			{
				if(curr==curr.parent.left)
				{
					curr.parent.left=curr.left;
					curr.left.parent=curr.parent;
				}
				else
				{
					curr.parent.right=curr.left;
					curr.left.parent=curr.parent;
				}
			}
			else
			{
				root=curr.left;
			}

			checkBalance(curr.parent);

			return ;
		}

		// subcase 2, only right child

		if(curr.right!=null&&curr.left==null)
		{
			if(curr.parent!=null)
			{
				if(curr==curr.parent.left)
				{
					curr.parent.left=curr.right;
					curr.right.parent=curr.parent;
				}
				else
				{
					curr.parent.right=curr.right;
					curr.right.parent=curr.parent;
				}
			}
			else
			{
				root=curr.right;
			}
			checkBalance(curr.parent);
			return ;
		}

		// case 3, both children present

		if(curr.left!=null && curr.right!=null)
		{
			Node<T> successor=successor(curr);
			T val=(T)successor.data;
			delete(root,(T)successor.data);
			curr.data=val;

			return ;
			
		}


	}

	public void add(T obj)
	{
		Node<T> node = new Node<T>(obj);
		if(root==null)
		{
			root=node;
			currentSize++;
			return ;
		}
		add(root,node);
	}

	public void add(Node<T> parent,Node<T> newNode)
	{
		if((((Comparable<T>)newNode.data).compareTo((T)parent.data))>0)
		{
			if(parent.right==null)
			{
				parent.right=newNode;
				newNode.parent=parent;
				currentSize++;
			}
			else
			{
				add(parent.right,newNode);
			}
		}
		else
		{
			if(parent.left==null)
			{
				parent.left=newNode;
				newNode.parent=parent;
				currentSize++;
			}
			else
			{
				add(parent.left,newNode);
			}
		}
		checkBalance(newNode);
	}

	public void checkBalance(Node<T> node)
	{
		if(((height(node.left)-height(node.right))>1) || ((height(node.left)-height(node.right))<-1))
		{
			rebalance(node);
		}
		if(node.parent==null)
			return;
		checkBalance(node.parent);
	}

	public int height(Node<T> node)
	{
		if(node==null)
			return 0;
		return (1+Math.max(height(node.left),height(node.right)));
	}

	public void rebalance(Node<T> node)
	{
		if(height(node.left)-height(node.right)>1)
		{
			if(height(node.left.left)>height(node.left.right))
			{
				if(node.parent!=null)
				{
					if(node.parent.left==node)
						node.parent.left=node.left;
					else
						node.parent.right=node.left;
				}
				node=rightRotate(node);
			}
			else
			{
				if(node.parent!=null)
				{
					if(node.parent.left==node)
						node.parent.left=node.left.right;
					else
						node.parent.right=node.left.right;
				}
				node=leftRightRotate(node);
			}
		}
		else
		{
	
			if(height(node.right.right)>height(node.right.left))
			{
				if(node.parent!=null)
				{
					if(node.parent.left==node)
						node.parent.left=node.right;
					else
						node.parent.right=node.right;
				}
				node=leftRotate(node);
			}
				
			else
			{
				if(node.parent!=null)
				{
					if(node.parent.left==node)
						node.parent.left=node.right.left;
					else
						node.parent.right=node.right.left;
				}

				node=rightLeftRotate(node);
			}
			
		}
		if(node.parent==null)
			root=node;
	}

	public Node<T> leftRotate(Node<T> node)
	{
		Node<T> temp=node.right;
		node.right=temp.left;
		if(temp.left!=null)
			temp.left.parent=node;
		temp.left=node;
		temp.parent=node.parent;
		node.parent=temp;
		return temp;
	}

	public Node<T> rightRotate(Node<T> node)
	{
		System.out.println("Yes");
		Node<T> temp=node.left;
		node.left=temp.right;
		if(temp.right!=null)
			temp.right.parent=node;
		temp.right=node;
		temp.parent=node.parent;
		node.parent=temp;
		return temp;
	}

	public Node<T> leftRightRotate(Node<T> node)
	{
		node.left=leftRotate(node.left);
		return rightRotate(node);

	}

	public Node<T> rightLeftRotate(Node<T> node)
	{
		node.right=rightRotate(node.right);
		return leftRotate(node);
	}

	public void preOrderTraversal(Node<T> node)
	{
		if(node==null)
			return ;
		System.out.print((T)(node.data)+" ");
		preOrderTraversal(node.left);
		preOrderTraversal(node.right);
	}

	public void inOrderTraversal(Node<T> node)
	{
		if(node==null)
			return ;
		inOrderTraversal(node.left);
		System.out.print((T)(node.data)+" ");
		inOrderTraversal(node.right);
	}

	public static void main(String[] args)
	{
		AVLTree<Integer> avl = new AVLTree<Integer>();
		avl.add(10);
		avl.add(20);
		avl.add(15);
		avl.add(25);
		avl.add(30);
		avl.add(16);
		avl.add(18);
		avl.add(19);
		System.out.println("PRE ORDER TRAVERSAL");
		avl.preOrderTraversal(avl.root);
		System.out.println("\n"+"IN ORDER TRAVERSAL");
		avl.inOrderTraversal(avl.root);
		System.out.println();
		avl.delete(18);
		System.out.println("\n"+"IN ORDER TRAVERSAL");
		avl.inOrderTraversal(avl.root);

	}

}





