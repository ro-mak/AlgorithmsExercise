public class AlgorithmsMain {

    public static void main(String[] args) {
        fibonacciNumbers(0,1,100);
    }

    // для 98 элементов 2883 операции, что приблизительно равно (n^2)/3,
    // а сложность варьируется согласно википедии в зависимости от отсортированности массива.
    // При отсортированном массиве в правильном порядке O(n), при отсортированном в обратном порядке O(n^2)
    public void sortInsert(int[] array) {
        int size = array.length;
        int operationsCount = 0;
        for (int elementI = 1; elementI < size; elementI++) {
            int tempInt = array[elementI];
            int elementJ = elementI;
            while (elementJ > 0 && array[elementJ - 1] >= tempInt) {
                array[elementJ] = array[elementJ - 1];
                elementJ--;
                operationsCount++;
            }
            array[elementJ] = tempInt;
            operationsCount++;
        }
        System.out.println("InsertSortOperations:" + operationsCount);
        System.out.println("size:" + size);
    }

    public static int fibonacciNumbers(int previousNumber,int currentNumber, int limit){
        if (currentNumber >= limit)return -1;
        if (previousNumber == 0) System.out.println(previousNumber);
        System.out.println(currentNumber);
        return fibonacciNumbers(currentNumber,previousNumber + currentNumber,limit);
    }

}
