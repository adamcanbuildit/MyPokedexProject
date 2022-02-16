// First define what a node is
class Node {
    constructor(value) {
        this.val=value;
        this.next=null;
    }
}


class SLL {
    constructor() {
        this.head=null;
    }

// ADD FRONT
    addFront(value) {
        let new_node = new Node(value);
        if(this.head==null) {
            this.head=new_node;
            return this;
        }
        //Must point the new front to the "current" head before making it head
        new_node.next=this.head;
        //Then set the new front as the head
        this.head=new_node;
        return this;
    }

// REMOVE FRONT
    removeFront() {
        if (this.head==null) {
            return null;
        }
        //Set the next node to be the head
        this.head=this.head.next;
        //Return the new SLL head
        return this;
    }

// FRONT (Return Value from Head Node)
    getFrontValue() {
        if (this.head==null) {
            return null;
        }
        return this.head.val;
    }
}


let test = new SLL();
console.log(test.addFront(2).addFront(4).addFront(5).addFront(1).removeFront().getFrontValue());