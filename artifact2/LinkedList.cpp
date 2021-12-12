//============================================================================
// Name        : LinkedList.cpp
// Author      : Greg Cook
// Version     : 1.0
// Copyright   : Copyright © 2017 SNHU COCE
// Description : Lists and Searching
//============================================================================

#include <algorithm>
#include <iostream>
#include <time.h>

#include "CSVparser.hpp"

using namespace std;

//============================================================================
// Global definitions visible to all methods and classes
//============================================================================

// forward declarations
double strToDouble(string str, char ch);

// define a structure to hold bid information
struct Bid {
    string bidId; // unique identifier
    string title;
    string fund;
    double amount;
    Bid() {
        amount = 0.0;
    }
};

// Internal structure for tree node
struct Node {
	Bid bid;
	Node* leftNode;
	Node* rightNode;

	// default constructor
	Node() {
		leftNode = nullptr;
		rightNode = nullptr;
	}

	// initialize node with a bid
	Node(Bid aBid) : Node() {
		bid = aBid;
	}

	int size = 0;
};

//============================================================================
// Binary Search Tree class definition
//============================================================================

/**
 * Define a class containing data members and methods to
 * implement a binary search tree
 */
class BinarySearchTree {

private:
	Node* root;

	void addNode(Node* node, Bid bid);
	void inOrder(Node* node);
	Node* removeNode(Node* node, string bidId);

public:
	BinarySearchTree();
	virtual ~BinarySearchTree();
	void InOrder();
	void Insert(Bid bid);
	void Remove(string bidId);
	Bid Search(string bidId);
};

/**
 * Default constructor
 */
BinarySearchTree::BinarySearchTree() {
	// initialize housekeeping variables
	root = nullptr;

}

/**
 * Destructor
 */
BinarySearchTree::~BinarySearchTree() {
	// recurse from root deleting every node
}

/**
 * Traverse the tree in order
 */
void BinarySearchTree::InOrder() {
	inOrder(root);
}
/**
 * Insert a bid
 */
void BinarySearchTree::Insert(Bid bid) {
	// Implement inserting a bid into the tree
	// Initialize a node
	Node* currNode = new Node(bid);

	// Determine if a root exists
	if (root == nullptr) {
		// This becomes the root
		root = currNode;
	}
	else {
		addNode(root, bid);
	}
}

/**
 * Remove a bid
 */
void BinarySearchTree::Remove(string bidId) {
	// Implement removing a bid from the tree

	removeNode(root, bidId);

}

/**
 * Remove a Node
 */
Node* BinarySearchTree::removeNode(Node* node, string bidId) {
	// If node is null return empty node
	if (node == nullptr) {
		return node;
	}

	// recurse down left subtree if given ID is less than node
	if (bidId.compare(node->bid.bidId) < 0) {
		node->leftNode = removeNode(node->leftNode, bidId);
	}
	else if (bidId.compare(node->bid.bidId) > 0) {
		node->rightNode = removeNode(node->rightNode, bidId);
	}
	else {
		// must be a leaf
		if (node->leftNode == nullptr && node->rightNode == nullptr) {
			delete node;
			node = nullptr;
		}
		// left child
		else if (node->leftNode != nullptr && node->rightNode == nullptr) {
			Node* temp = node;
			node = node->leftNode;
			delete temp;
		}
		// right child
		else if (node->rightNode != nullptr && node->leftNode == nullptr) {
			Node* temp = node;
			node = node->rightNode;
			delete temp;
		}
		// both left and right children
		else {
			Node* temp = node->rightNode;
			while (temp->leftNode != nullptr) {
				temp = temp->leftNode;
			}
			node->bid = temp->bid;
			node->rightNode = removeNode(node->rightNode, temp->bid.bidId);
		}
	}
	return node;

}

/**
 * Search for a bid
 */
Bid BinarySearchTree::Search(string bidId) {
	// Implement searching the tree for a bid

	// start at the root
	Node* currNode = root;

	// loop until id is found or bottom reached
	while (currNode != nullptr) {
		// is node a match for the search
		if (currNode->bid.bidId.compare(bidId) == 0) {
			return currNode->bid;
		}
		else if (currNode->bid.bidId.compare(bidId) > 0) {
			// check left if less than current, and right if greater than current
			currNode = currNode->leftNode;
		}
		else {
			currNode = currNode->rightNode;
		}
	}

	Bid bid;
	return bid;
}

/**
 * Add a bid to some node (recursive)
 *
 * @param node Current node in tree
 * @param bid Bid to be added
 */
void BinarySearchTree::addNode(Node* node, Bid bid) {
	// Implement inserting a bid into the tree
	// root exists so add the node in the tree

	if (node->bid.bidId.compare(bid.bidId) > 0) {
		// place in current node's left child if it is empty
		if (node->leftNode == nullptr) {
			node->leftNode = new Node(bid);
		}
		else {
			// move down the left side of the tree
			addNode(node->leftNode, bid);
		}
	}
	else {
		// place in current node's right child if it is empty
		if (node->rightNode == nullptr) {
			node->rightNode = new Node(bid);
		}
		else {
			// move down the left side of the tree
			addNode(node->rightNode, bid);
		}
	}

}
void BinarySearchTree::inOrder(Node* node) {
	if (node != nullptr) {
		inOrder(node->leftNode);
		cout << node->bid.bidId << ": " << node->bid.title << " | "
			<< node->bid.amount << " | " << node->bid.fund << endl;
		inOrder(node->rightNode);

	}
}
//============================================================================
// Linked-List class definition
//============================================================================

/**
 * Define a class containing data members and methods to
 * implement a linked-list.
 */
class LinkedList {

private:
    // Internal structure for list entries, housekeeping variables
	struct Node {
		Bid bid;
		Node* nextNode;

		// default constructor
		Node() {
			nextNode = nullptr;
		}

		// initialize node with a bid
		Node(Bid aBid) {
			bid = aBid;
			nextNode = nullptr;
		}
	};

	Node* headNode;
	Node* tailNode;
	int size = 0;


public:
    LinkedList();
    virtual ~LinkedList();
    void Append(Bid bid);
    void Prepend(Bid bid);
    void PrintList();
    void Remove(string bidId);
    Bid Search(string bidId);
	void Sort(BinarySearchTree* bst);
    int Size();
};

/**
 * Default constructor
 */
LinkedList::LinkedList() {
    // Initialize housekeeping variables
	headNode = nullptr;
	tailNode = nullptr;
	}


/**
 * Destructor
 */
LinkedList::~LinkedList() {
}

/**
 * Append a new bid to the end of the list
 */
void LinkedList::Append(Bid bid) {
    // Implement append logic
	Node* currNode = new Node(bid);

	if (headNode == nullptr) {  			// is this a new list?
		headNode = currNode;				// currNode becomes headNode
	} else {
		if (tailNode != nullptr) {			// is there already a tailNode?
			tailNode->nextNode = currNode;	// current tailNode's nextNode is currNode
		}
	}

	tailNode = currNode;					// appending so currNode is always new tailNode
	size++;


}

/**
 * Prepend a new bid to the start of the list
 */
void LinkedList::Prepend(Bid bid) {
    // Implement prepend logic
	Node* currNode = new Node(bid);

	if (headNode != nullptr) {
		currNode->nextNode = headNode;
	} else {
		tailNode = currNode;
	}

	headNode = currNode;
	size++;
}

/**
 * Simple output of all bids in the list
 */
void LinkedList::PrintList() {
    // Implement print logic
	Node* currNode = headNode;

	// make sure bids have been loaded and loop through all pointers
	while (currNode != nullptr) {
		cout << currNode->bid.bidId << ": " << currNode->bid.title << " | "
			 << currNode->bid.fund << " | " << currNode->bid.amount << endl;
		currNode = currNode->nextNode;
	}
}

/**
 * Remove a specified bid
 *
 * @param bidId The bid id to remove from the list
 */
void LinkedList::Remove(string bidId) {
    // Implement remove logic
	if (headNode != nullptr) {
		if (headNode->bid.bidId.compare(bidId) == 0) {
			Node* tempNode = headNode->nextNode;
			delete headNode;
			headNode = tempNode;
		}
	}

	Node* currNode = headNode;

	// loop through nodes comparing the bidId after current node to bidId
	while (currNode->nextNode != nullptr) {
		if (currNode->nextNode->bid.bidId.compare(bidId) == 0){
			// save the node after the current node
			Node* tempNode = currNode->nextNode;

			// make the current node point to the node after the next node
			currNode->nextNode = tempNode->nextNode;

			// delete tempNode which is what user selected
			delete tempNode;

			size--;

			return;
		}
		currNode = currNode->nextNode;
	}


}

/**
 * Search for the specified bidId
 *
 * @param bidId The bid id to search for
 */
Bid LinkedList::Search(string bidId) {
    // Implement search logic
	Node* currNode = headNode;

	// make sure bids have been loaded and loop through bids comparing bidId

   	while (currNode != nullptr) {
   		if (currNode->bid.bidId.compare(bidId) == 0) {
   			return currNode->bid;
   		}
   		currNode = currNode->nextNode;
   	}

}

/**
 * Place nodes into binary search tree
 *
 * @param bst the binary search tree to insert the nodes into
 */
void LinkedList::Sort(BinarySearchTree* bst) {
	// Implement search logic
	Node* currNode = headNode;

	// make sure bids have been loaded and loop through bids comparing bidId

	while (currNode != nullptr) {
		
		bst->Insert(currNode->bid);
		currNode = currNode->nextNode;
	}

}

/**
 * Returns the current size (number of elements) in the list
 */
int LinkedList::Size() {
    return size;
}

//============================================================================
// Static methods used for testing
//============================================================================

/**
 * Display the bid information
 *
 * @param bid struct containing the bid info
 */
void displayBid(Bid bid) {
    cout << bid.bidId << ": " << bid.title << " | " << bid.amount
         << " | " << bid.fund << endl;
    return;
}

/**
 * Prompt user for bid information
 *
 * @return Bid struct containing the bid info
 */
Bid getBid() {
    Bid bid;

    cout << "Enter Id: ";
    cin.ignore();
    getline(cin, bid.bidId);

    cout << "Enter title: ";
    getline(cin, bid.title);

    cout << "Enter fund: ";
    cin >> bid.fund;

    cout << "Enter amount: ";
    cin.ignore();
    string strAmount;
    getline(cin, strAmount);
    bid.amount = strToDouble(strAmount, '$');

    return bid;
}

/**
 * Load a CSV file containing bids into a LinkedList
 *
 * @return a LinkedList containing all the bids read
 */
void loadBids(string csvPath, LinkedList *list) {
    cout << "Loading CSV file " << csvPath << endl;

    // initialize the CSV Parser
    csv::Parser file = csv::Parser(csvPath);

    try {
        // loop to read rows of a CSV file
        for (int i = 0; i < file.rowCount(); i++) {

            // initialize a bid using data from current row (i)
            Bid bid;
            bid.bidId = file[i][1];
            bid.title = file[i][0];
            bid.fund = file[i][8];
            bid.amount = strToDouble(file[i][4], '$');

            //cout << bid.bidId << ": " << bid.title << " | " << bid.fund << " | " << bid.amount << endl;

            // add this bid to the end
            list->Append(bid);
        }
    } catch (csv::Error &e) {
        std::cerr << e.what() << std::endl;
    }
}

/**
 * Simple C function to convert a string to a double
 * after stripping out unwanted char
 *
 * credit: http://stackoverflow.com/a/24875936
 *
 * @param ch The character to strip out
 */
double strToDouble(string str, char ch) {
    str.erase(remove(str.begin(), str.end(), ch), str.end());
    return atof(str.c_str());
}

/**
 * The one and only main() method
 *
 * @param arg[1] path to CSV file to load from (optional)
 * @param arg[2] the bid Id to use when searching the list (optional)
 */
int main(int argc, char* argv[]) {

    // process command line arguments
    string csvPath, bidKey;
    switch (argc) {
    case 2:
        csvPath = argv[1];
        bidKey = "98109";
        break;
    case 3:
        csvPath = argv[1];
        bidKey = argv[2];
        break;
    default:
        csvPath = "eBid_Monthly_Sales_Dec_2016.csv";
        bidKey = "98109";
    }

	// flag to determine type of data structure is used in searching
	// raised when sorted and lowered when list is modified
	bool sorted = false;

    clock_t ticks;

    LinkedList bidList;

    Bid bid;

	BinarySearchTree* bst;

    int choice = 0;
    while (choice != 9) {
        cout << "Menu:" << endl;
        cout << "  1. Enter a Bid" << endl;
        cout << "  2. Load Bids" << endl;
        cout << "  3. Display All Bids" << endl;
        cout << "  4. Find Bid" << endl;
        cout << "  5. Remove Bid" << endl;
		cout << "  6. Sort Bids" << endl;
        cout << "  9. Exit" << endl;
        cout << "Enter choice: ";
        cin >> choice;

        switch (choice) {
        case 1:
            bid = getBid();
            bidList.Append(bid);
			sorted = false;
            displayBid(bid);

            break;

        case 2:
            ticks = clock();

			// load data into list and lower sorted flag
            loadBids(csvPath, &bidList);
			sorted = false;

            cout << bidList.Size() << " bids read" << endl;

            ticks = clock() - ticks; // current clock ticks minus starting clock ticks
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;

            break;

        case 3:
			// check sorted flag to determine if data will be displayed sorted
			if (sorted) {
				bst->InOrder();
			}
			else {
				bidList.PrintList();
			}

            break;

        case 4:
            ticks = clock();

			// check sorted flag to determine best search method to use
            if (bidList.Size() != 0) {
				if (!sorted) {
					bid = bidList.Search(bidKey);
				}
				else {
					bid = bst->Search(bidKey);
				}
            	
            } else {
            	cout << "You must load bids before searching" << endl;
            }

            ticks = clock() - ticks; // current clock ticks minus starting clock ticks

            if (!bid.bidId.empty()) {
                displayBid(bid);
            } else {
            	cout << "Bid Id " << bidKey << " not found." << endl;
            }

            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;

            break;

        case 5:
            bidList.Remove(bidKey);
			sorted = false;

            break;

		case 6:
			// define the new tree and sort the bids from the linked list into the tree
			// only needed if list has been modified
			bst = new BinarySearchTree;
			bidList.Sort(bst);
			sorted = true;
			break;
        }
    }

    cout << "Good bye." << endl;

    return 0;
}
