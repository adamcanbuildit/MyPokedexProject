
let myArr = [1, 2, 3, 4, 5, 6, 7, 8, 9];


// Given a numerical array, reverse the order of values, in-place. The reversed array should have the same length, 
// with existing elements moved to other indices so that order of elements is reversed. Working 'in-place' means that you cannot use a second 
// array – move values within the array that you are given. As always, do not use built-in array functions such as splice().
function reverseOrder(array) {
    for (let i = 0; i <= array.length / 2 - 1; i++) {
        let val1 = array[i];
        let val2 = array[array.length - 1 - i];
        array[i] = val2;
        array[array.length - 1 - i] = val1;
    }
}

reverseOrder(myArr);
console.log(myArr);








// Implement rotateArr(arr, shiftBy) that accepts array and offset. Shift arr's values to the right by that amount.
// 'Wrap-around' any values that shift off array's end to the other side, so that no data is lost.
// Operate in-place: given ([1,2,3],1), change the array to [3,1,2]. Don't use built-in functions.
// Second: allow negative shiftBy (shift L, not R).
// Third: minimize memory usage. With no new array, handle arrays/shiftBys in the millions.
// -Try modulus ? ( maybe something like shiftBy%arr.length )
// Fourth: minimize the touches of each element.
function rotateArray(arr, shiftBy) {
    let shiftCount = shiftBy % arr.length;
    // if >0 shift to the right
    if (shiftBy > 0) {
        for (let i = 0; i < shiftCount; i++) {  // runs x times where x is the number of shifts to make
            let temp = arr[arr.length - 1];  // Store final value in array
            // Set each element in the array equal to the one preceding it
            for (let j = arr.length - 1; j > 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[0] = temp;  // Set the first element in array equal to the stored temp value
        }
    }
    // else if <0 shift to the left
    else if (shiftBy < 0) {
        let actualShift = Math.abs(shiftCount);
        for (let i = 0; i < actualShift; i++) { // runs x times where x is the number of shifts to make
            let temp = arr[0]; // Store first value in array
            // Set each element in array equal to the one following it
            for (let j = 0; j < arr.length - 1; j++) {
                arr[j] = arr[j + 1];
            }
            arr[arr.length - 1] = temp; // Set the last element in array equal to the stored temp value
        }
    }
    // else shift is 0, so do nothing
    else {
        return;
    }
}

let shift = -24;
rotateArray(myArr, shift);
console.log(myArr);






// Alan is good at breaking secret codes. One method is to eliminate values that lie outside of a specific known range.
// Given arr and values min and max, retain only the array values between min and max.
// Work in-place: return the array you are given, with values in original order. No built-in array functions.
function eliminateValues(array, min, max) {
    // count will keep track of # of values eliminated
    let count = 0;
    // iterate over the array
    for (let i = 0; i < array.length; i++) {
        // first check if the final value in the array is valid, if it's not overwrite it with a valid value (max)
        // We will delete this entry later. But it prevents a possible infinite loop 
        if (array[array.length - 1] < min || array[array.length - 1] > max) {
            array[array.length - 1] = max;
            count++;
        }
        // if array[i] is above or below the range overwrite it
        if (array[i] < min || array[i] > max) {
            // while array[i] is still out of range overwrite it
            // this handles instances of back-to-back out of range values
            while (array[i] < min || array[i] > max) {
                // shift all the values to the left by one spot
                for (let j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1]
                }
                // increase count of removed values
                count++;
            }
        }
    }
    for (let k = 0; k < count; k++) {
        array.pop()
    }
}

let min = 2;
let max = 8;
console.log(myArr);
eliminateValues(myArr, min, max);
console.log(myArr);










// Replicate JavaScript's concat(). Create a standalone function that accepts two arrays.
// Return a new array containing the first array's elements, followed by the second array's elements. Do not alter the original arrays.
// Ex.: arrConcat( ['a','b'], [1,2] ) should return new array ['a','b',1,2].
function concatTwoArrays(arr1, arr2) {
    let newArr = arr1;
    for (let i = 0; i < arr2.length; i++) {
        newArr.push(arr2[i]);
    }
    return newArr;
}

let array1 = [1, true, "hi"];
let array2 = [2, false, "bye"];
console.log(concatTwoArrays(array1, array2));
