package AssignmentNine;

import java.util.Stack;

/**
 * This class is a Binary Search Tree. This class has the ability
 * to add new element as their child. Each node can only have 
 * left and right child at most.
 * @author Pongsakorn Cherngchaosil 012107171
 *
 */
public class BinarySearchTree 
{
	/**
	 * Enum for BinarySearchTree, used for when traversing.
	 * @author Pongsakorn Cherngchaosil 012107171
	 *
	 */
	public enum Direction
	{
		TOP,LEFT,RIGHT
	}
	
	/**
	 * Enum for BinarySearchTree, used for when traversing.
	 * @author Pongsakorn Cherngchaosil 012107171
	 *
	 */
	public enum TraversalOrder
	{
		PREORDER,INORDER,POSTORDER
	}
	
	/**
	 * Traverse the tree in order according to the parameter.
	 * @param order The order in which to traverse the tree.
	 */
	public void traverse(TraversalOrder order)
	{
		Stack<Node> s = new Stack<Node>();
		mRoot.mDirection = BinarySearchTree.Direction.TOP;
		s.push(mRoot);
		boolean firstPreOrder = true;
		boolean firstPostOrder = true;
		boolean firstInOrder = true;
		while(!s.isEmpty())
		{
			Node current = s.pop();
			if(current.mDirection == BinarySearchTree.Direction.TOP)
			{
				if(order == TraversalOrder.PREORDER)
				{
					if(firstPreOrder)
					{
						System.out.println("PREORDER");
						firstPreOrder = false;
					}
					current.visit();
				}
				current.mDirection = BinarySearchTree.Direction.LEFT;
				s.push(current);

				if(current.mLeftChild != null)
				{
					Node next = current.mLeftChild;
					next.mDirection = BinarySearchTree.Direction.TOP;
					s.push(next);
				}
			}
			else if(current.mDirection == BinarySearchTree.Direction.LEFT)
			{
				if(order == TraversalOrder.INORDER)
				{
					if(firstInOrder)
					{
						System.out.println("INORDER");
						firstInOrder = false;
					}
					current.visit();
				}
				current.mDirection = BinarySearchTree.Direction.RIGHT;
				s.push(current);

				if(current.mRightChild != null)
				{
					Node next = current.mRightChild;
					next.mDirection = BinarySearchTree.Direction.TOP;
					s.push(next);
				}
			}
			else // Coming up from the right
			{
				if(order == TraversalOrder.POSTORDER)
				{	
					if(firstPostOrder)
					{
						System.out.println("POSTORDER");
						firstPostOrder = false;
					}
					current.visit();
				}
			}
		}
	}
	
	/**
	 * Evaluate the tree using Postorder
	 * @return The evaluated value of the tree
	 */
	public int evaluate()
	{
		int total = 0;
		TraversalOrder order1 = TraversalOrder.POSTORDER;
		Stack<Node> s1 = new Stack<Node>();
		mRoot.mDirection = BinarySearchTree.Direction.TOP;
		s1.push(mRoot);
		while(!s1.isEmpty())
		{
			Node current = s1.pop();
			if(current.mDirection == BinarySearchTree.Direction.TOP)
			{
				current.mDirection = BinarySearchTree.Direction.LEFT;
				s1.push(current);

				if(current.mLeftChild != null)
				{
					Node next = current.mLeftChild;
					next.mDirection = BinarySearchTree.Direction.TOP;
					s1.push(next);
				}
			}
			else if(current.mDirection == BinarySearchTree.Direction.LEFT)
			{
				current.mDirection = BinarySearchTree.Direction.RIGHT;
				s1.push(current);

				if(current.mRightChild != null)
				{
					Node next = current.mRightChild;
					next.mDirection = BinarySearchTree.Direction.TOP;
					s1.push(next);
				}
			}
			else // Coming up from the right
			{
				if(order1 == TraversalOrder.POSTORDER)
				{	
					total = current.evaluateNode();
				}
			}
		}
		return total;
	}

	/**
	 * Insert a new Node with key and value as a child.
	 * @param key The key attribute of the new node
	 * @param value The value attribute of the new node
	 */
	public void insert(String pKey, String pValue)
	{
		Node newNode = new Node(pKey, pValue);
		if(mRoot == null)
			mRoot = newNode;
		else
			mRoot.addNode(newNode);
	}
	
	/**
	 * Helper method to finding the key
	 * @param key The key to look up for
	 * @return Return true if found, false otherwise.
	 */
	public boolean isPresent(String pKey)
	{
		return mRoot.isPresent(pKey);
	}
	
	/**
	 * Private Node class for use for BinarySearchTree
	 * @author Pongsakorn Cherngchaosil 012107171
	 *
	 */
	private class Node
	{
		/**
		 * Construct a new Node object with properties Key and Value
		 * @param pKey
		 * @param pValue
		 */
		public Node(String pKey, String pValue)
		{
			mKey = pKey;
			mValue = pValue;
			mEval = 0;
			mLeftChild = null;
			mRightChild = null;
		}
		
		/**
		 * Prints out the key and value of the Node
		 */
		public void visit()
		{
			System.out.println("("+mKey+","+mValue+")");
		}
		
		/**
		 * Add a new Node according the value of the new Node.
		 * @param pNewNode The new Node to be added.
		 */
		public void addNode(Node pNewNode)
		{
			Node mCurrent = this;
			// Comparing the newly-added Node's key with the root's key
			if(pNewNode.mKey.charAt(0) < mCurrent.mKey.charAt(0))
			{
				// if the left child is null, add the new Node
				if(mCurrent.mLeftChild == null)
				{
					mLeftChild = pNewNode;
				}
				// if not left check if there is still a left child
				else
				{
					while(mCurrent.mLeftChild != null)
					{
						mCurrent = mCurrent.mLeftChild;
						if(pNewNode.mKey.charAt(0) < mCurrent.mKey.charAt(0))
						{
							if(mCurrent.mLeftChild == null)
							{
								mCurrent.mLeftChild = pNewNode;
							}
							mCurrent = mCurrent.mLeftChild;
						}
						else if(pNewNode.mKey.charAt(0) > mCurrent.mKey.charAt(0))
						{
							if(mCurrent.mRightChild == null)
							{
								mCurrent.mRightChild = pNewNode;
							}
							mCurrent = mCurrent.mRightChild;
						}
					}
				}
			}
			else if(pNewNode.mKey.charAt(0) > mCurrent.mKey.charAt(0))
			{
				if(mCurrent.mRightChild == null)
				{
					mRightChild = pNewNode;
				}
				else
				{
					while(mCurrent.mRightChild != null)
					{
						mCurrent = mCurrent.mRightChild;
						if(pNewNode.mKey.charAt(0) > mCurrent.mKey.charAt(0))
						{
							if(mCurrent.mRightChild == null)
							{
								mCurrent.mRightChild = pNewNode;
							}
							mCurrent = mCurrent.mRightChild;
						}
						else 
						{
							if(mCurrent.mLeftChild == null)
							{
								mCurrent.mLeftChild = pNewNode;
							}
							mCurrent = mCurrent.mLeftChild;
						}
					}
				}
			}
		}
		
		/**
		 * Helper method to finding the key
		 * @param key The key to look up for
		 * @return Return true if found, false otherwise.
		 */
		public boolean isPresent(String pKey)
		{
			Node current = this;
			while(current != null)
			{
				int i = pKey.compareTo(current.mKey);
				if(i == 0)
				{
					return true;
				}
				else if(i > 0)
				{
					current = current.mRightChild;
				}
				else
					current = current.mLeftChild;
			}
			return false;
		}
		
		/**
		 * Evaluate the Node
		 * @return The value of the Node
		 */
		public int evaluateNode()
		{

			if(mValue.charAt(0) == '+')
			{
				mEval = mLeftChild.mEval+mRightChild.mEval;
				System.out.println("Evaluated: "+"("+mKey+","+mValue+"). Result: "+mEval);
				return mEval;
			}
			else if(mValue.charAt(0) == '-')
			{
				mEval = mLeftChild.mEval-mRightChild.mEval;
				System.out.println("Evaluated: "+"("+mKey+","+mValue+"). Result: "+mEval);
				return mEval;
			}
			else if(mValue.charAt(0) == '/')
			{
				mEval = mLeftChild.mEval/mRightChild.mEval;
				System.out.println("Evaluated: "+"("+mKey+","+mValue+"). Result: "+mEval);
				return mEval;
			}
			else if(mValue.charAt(0) == '*')
			{
				mEval = mLeftChild.mEval*mRightChild.mEval;
				System.out.println("Evaluated: "+"("+mKey+","+mValue+"). Result: "+mEval);
				return mEval;
			}
			else
			{
				mEval = Integer.parseInt(mValue);
				System.out.println("Evaluated: "+"("+mKey+","+mValue+"). Result: "+mEval);
				return mEval;
			}
		}
		private String mKey;
		private String mValue;
		private Node mLeftChild;
		private Node mRightChild;
		private int mEval;
		private BinarySearchTree.Direction mDirection;
	}
	private Node mRoot;
}
