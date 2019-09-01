package cn.datastructure.com;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class Sort {
    public static void main(String[] args) {
//        int[] array = {99,12,56,38,64,77,22,9,81,100,0};
//        System.out.println(sort(array,101));
        //int[] array = {8,0,1,5,2,3,99,44,22};
        //System.out.println(BinarySearch(array,11,0,11));
//        Random random = new Random();
//        int[] arr =new int[10000];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = random.nextInt(10000)+1;
//
//        }
//        insertSort(arr);
//        shellaSort(arr);
//        quickSort(arr);
        int[] arr = {10,6,7,1,3,9,4,2,5,7,8,9,11,55,66,88,0,22,66,33,333,3332,668};
        mrgeSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    public static int sort(int[] arr,int key) {
        //时间复杂度是T(n) = O(n),空间复杂度是S(n) = O(1)
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i ;
            }
        }
        return -1;
    }
    public static int BinarySearch(int[] arr,int key) {
        //二分查找非递归 时间复杂度是T(n) = O(logn),空间复杂度是S(n) = O(1)
        int low = 0;
        int high = arr.length-1;
        while(low <= high) {
            int mid = (low + high) >>1;
            if (arr[mid] > key) {
                high = mid-1;
            }else if(arr[mid] < key) {
                low = mid+1;
            }else {
                return mid;
            }
        }
        return -1;
    }
    public static int BinarySearch(int[] arr,int key,int low,int high) {
        //递归实现二分查找 时间复杂度为T(n) = O(logn),空间复杂度为S(n) = O(logn)
        if (low > high) {
            return -1;
        }
        int mid = (low + high) >> 1;
        if (arr[mid] == key) {
            return mid;
        }else if (arr[mid] > key) {
            return BinarySearch(arr,key,low,mid-1);
        }else if(arr[mid] < key) {
            return BinarySearch(arr,key,low+1,high);
        }
        return -1;
    }
    //把这棵树一次调整为大根堆的时间复杂度是多少O(logn)
    public static void adjust(int[] arr,int start,int end) {
        int tmp = arr[start];
        for (int i = 2*start+1; i <= end; i= i*2+1) {
            //找到左右孩子的最大值的下标
            if ((i< end) && arr[i]<arr[i+1] ) {
                i++;//找到左右孩子的最大值
            }
            //arr[i]>tmp
            if (arr[i] > tmp) {
                arr[start] = arr[i];
                start = i;
            }
            if (arr[i] < tmp) {
                //arr[start] = tmp;
                break;
            }
        }
        arr[start] = tmp;
    }
    public static void heapSort(int[] arr) {
        //整棵树调整为大根堆的时间复杂度是O(nlogn)
        for (int i = (arr.length-1-1)/2; i >= 0; i--) {
            adjust(arr,i,arr.length-1);
        }
        for (int j = 0; j < arr.length-1; j++) {
            int tmp = arr[arr.length-1-j];
            arr[arr.length-1-j] = arr[0];
            arr[0] = tmp;
            //调整的时候不需要调整有序的数据
            adjust(arr,0,arr.length-1-j-1);
        }
    }
    private static int partation(int[] arr,int low,int high) {
//        int i = low;
//        int j = high;
        int x = arr[low];
        while(low < high) {
            while(arr[high] >= x && low<high) {
                high--;
            }
            if (low < high) {
                arr[low] = arr[high];
                low++;
            }
            while(arr[low] < x && low <high) {
                low++;
            }
            if (low < high) {
                arr[high] = arr[low];
                high--;
            }
        }
        arr[low] = x;
        return low;
    }
    private static void insertQuickSort(int[] arr,int low,int high) {
        int tmp = 0;
        for (int i = low+1; i <= high; i++) {
            tmp = arr[i];
            int j = 0;
            for (j = i-1; j >= low; j--) {
                if (arr[j] > tmp) {
                    arr[j+1] = arr[j];
                }else {
                    break;
                }
            }
            arr[j+1] = tmp;
        }
    }
    private static void quickSort(int[] arr,int low,int high) {
        //快速排序的优化  方法一：当它的数量小的时候直接进行直接插入排序
        if (high - low + 1 <= 16) {
            insertQuickSort(arr,low,high);
        }
        if (low < high) { //递归的结束条件
            int index = partation(arr,low,high);
            quickSort(arr,low,index-1);
            quickSort(arr,index+1,high);
        }
    }
    //快速排序
    public static void quickSort(int[] arr) {
        int low = 0;
        int high = arr.length-1;
        long s = System.currentTimeMillis();
        quickSort(arr,low,high);
        long e = System.currentTimeMillis();
        System.out.println(s-e);
    }
    //快速排序非递归
    public static void quickSortNotRec(int[] arr) {
        Deque<Integer> stack = new LinkedList<>();
        int low = 0;
        int high = arr.length-1;
        int par = partation(arr,low,high);
        if (low < high) {
            stack.add(low);
            stack.add(par-1);
            stack.add(par+1);
            stack.add(high);
        }
        while(stack.size() > 0 ) {
            high = stack.poll();
            low = stack.poll();
            par = partation(arr,low,high);
            if (low < high) {
                stack.add(low);
                stack.add(par-1);
                stack.add(par+1);
                stack.add(high);
            }
        }
    }
    public static void merge(int[] arr,int low ,int mid,int high) {
        int[] tmp = new int[arr.length];
        int index = low;
        int low2 = mid+1;
        int i = low;
        while(low <= mid && low2<= high) {
            if (arr[low] <= arr[low2]) {
                tmp[index++] = arr[low++];
            }else {
                tmp[index++] = arr[low2++];
            }
        }
        while(low <= mid) {
            tmp[index++] = arr[low++];
        }
        while(low2 <= high) {
            tmp[index++] = arr[low2++];
        }
        while(i <= high) {
            arr[i] = tmp[i];
            i++;
        }
        System.out.println(Arrays.toString(arr));
    }
    public static void mrgeSort(int[] arr,int low,int high) {
        if (low >= high) {
            return;
        }
        int mid = (low + high)/2;
        mrgeSort(arr,low,mid);
        mrgeSort(arr,mid+1,high);
        merge(arr,low,mid,high);
    }
    //选择排序
    //判断数据是否稳定看数据是否有跳跃性的变化 时间复杂度是O(n2),空间复杂度是O(1) 不稳定
    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j= i+1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    int tmp = 0;
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }
    public static void swap(int[] arr,int start,int end){
        int tmp = arr[start];
        arr[start] = arr[end];
        arr[end] = tmp;
    }
    public static void midOfThree(int[] arr,int low,int high) {
        int mid = (low+high)>>>1;

    }
    //直接插入排序，越有序越快
    public static void insertSort(int[] arr) {
        long s = System.currentTimeMillis();
        int tmp = 0;
        for (int i = 1; i < arr.length; i++) {
            tmp = arr[i];
            int j = 0;
            for (j = i-1; j >=0 ; j--) {
                if(arr[j] > tmp) {
                    arr[j+1] = arr[j];
                }else {
                    break;
                }
            }
            arr[j+1] = tmp;
        }
        long e = System.currentTimeMillis();
        System.out.println(s-e);
    }
    //希尔排序
    public static void shell(int[] array,int tag) {
        int tmp = 0;
        for (int i = tag; i < array.length; i++) {
            tmp = array[i];
            int j = 0;
            for (j = i-tag; j >= 0 ; j-=tag) {
                if (array[j] > tmp) {
                    array[j+tag] = array[j];
                }else {
                    break;
                }
            }
            array[j+tag] = tmp;
        }
    }
    public static void shellaSort(int[] arr) {
        long s = System.currentTimeMillis();
        int[] drr = {5,3,1};
        for (int i = 0; i < drr.length; i++) {
            shell(arr,drr[i]);
        }
        long e = System.currentTimeMillis();
        System.out.println(s-e);
    }
}
