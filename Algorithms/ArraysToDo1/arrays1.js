// Test variables
let myArr = ["first", 2, 3, "red", true, 28, 3.14, "end"];
let string = "Adam";

//Push Front
//Given an array and an additional value, insert this value at the beginning of the array. 
//Do this without using any built-in array methods.
function pushFront(arr, val) {
    let length = arr.length;
    for (i = length; i > 0; i--) {
        arr[i] = arr[i - 1];
    }
    arr[0] = val;
    return arr;
}

console.log(pushFront(myArr, string));



//Pop Front
//Given an array, remove and return the value at the beginning of the array.
//Do this without using any built-in array methods except pop().
function popFront(arr) {
    let val = arr[0]
    for (let i = 0; i < arr.length - 1; i++) {
        arr[i] = arr[i + 1];
    }
    arr.pop();
    return val;
}

console.log(popFront(myArr));
console.log(myArr);



//Insert At
//Given an array, index, and additional value, insert the value into array at given index.
//Do this without using built-in array methods. You can think of pushFront(arr,val) as equivalent to insertAt(arr,0,val).
function insertAt(array, index, value) {
    let length = array.length;
    if (index == 0) {
        for (i = length; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = value;
    } else {
        for (i = length; i >= index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = value;
    }
}

insertAt(myArr, 2, string);
console.log(myArr);



//Remove At (Bonus Challenge)0
//Given an array and an index into array, remove and return the array value at that index.
//Do this without using built-in array methods except pop(). Think of popFront(arr) as equivalent to removeAt(arr,0).
function removeAt(array, index) {
    let val = array[index];
    for (let i = index; i < array.length - 1; i++) {
        array[i] = array[i + 1];
    }
    array.pop();
    return val;
}

console.log(removeAt(myArr, 4));
console.log(myArr);



//Swap Pairs (Bonus Challenge)
//Swap positions of successive pairs of values of given array. If length is odd, do not change the final element.
//For [1,2,3,4], return [2,1,4,3]. For example, change input ["Brendan",true,42] to [true,"Brendan",42]. As with all array challenges, do this without using any built-in array methods.




//Remove Duplicates (Bonus Challenge)
//Sara is looking to hire an awesome web developer and has received applications from various sources.
//Her assistant alphabetized them but noticed some duplicates. Given a sorted array, remove duplicate values.
//Because array elements are already in order, all duplicate values will be grouped together.
//As with all these array challenges, do this without using any built-in array methods.