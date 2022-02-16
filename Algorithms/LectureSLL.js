// SLL Lecture follow along for Coding Dojo (Projects And Algos - lecture 2)

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
}