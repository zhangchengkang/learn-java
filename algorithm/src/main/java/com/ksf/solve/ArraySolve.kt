package com.ksf.solve

import kotlin.math.max

//fun main() {
//    val arr = intArrayOf(64, 22, 34, 25, 12, 22, 11, 90)
//    heapSort(arr)
//    println("排序后的数组：")
//    for (element in arr) {
//        print("$element ")
//    }
//}

/**
 * 冒泡排序（Bubble Sort）：依次比较相邻的元素，如果顺序不对则交换，重复这个过程直到整个数组排序完成。

 * 选择排序（Selection Sort）：每次从未排序部分选择最小（或最大）的元素放到已排序部分的末尾。

 * 插入排序（Insertion Sort）：每次将一个待排序的元素插入到已经排好序的数组中的适当位置。

 * 归并排序（Merge Sort）：将数组分成两个子数组，分别排序后合并。

 * 快速排序（Quick Sort）：选择一个基准元素，将小于基准的元素放到左边，大于基准的元素放到右边，然后递归地对左右两部分进行排序。

 * 堆排序（Heap Sort）：利用堆这种数据结构进行排序，通过构建最大堆或最小堆来实现排序。

 * 希尔排序（Shell Sort）：插入排序的改进版，通过设置间隔对元素进行插入排序，逐渐减少间隔直至1，最终完成排序。

 * 计数排序（Counting Sort）：适用于一定范围内的整数排序，通过统计元素出现的次数来实现排序。

 * 桶排序（Bucket Sort）：将元素分配到不同的桶中，对每个桶中的元素进行排序，然后按顺序合并各个桶的结果。
 */

//冒泡排序
fun bubbleSort(arr: IntArray) {
    //每次循环确定最大值，放到最后
    for (i in 0 until arr.size - 1) {
        var swapped = false
        //把大的往后交换，循环完最大的就在后面了
        for (j in 0 until arr.size - i - 1) {
            if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
                swapped = true
            }
        }
        // 如果在一轮遍历中没有发生交换，说明数组已经有序，可以提前结束排序
        if (!swapped) break
    }
}

//选择排序
fun selectionSort(arr: IntArray) {
    for (i in 0 until arr.size - 1) {
        var minIndex = i
        for (j in i + 1 until arr.size) {
            if (arr[minIndex] > arr[j]) minIndex = j
        }

        if (minIndex != i) {
            val temp = arr[minIndex]
            arr[minIndex] = arr[i]
            arr[i] = temp
        }
    }
}

//插入排序
fun insertionSort(arr: IntArray) {
    for (i in 1 until arr.size) {
        val temp = arr[i]
        var j = i - 1

        while (j >= 0 && temp < arr[j]) {
            arr[j + 1] = arr[j]
            j--
        }

        arr[j + 1] = temp
    }

}

//快速排序
fun quickSort(arr: IntArray) {
    quickSort(arr, 0, arr.size - 1)
}

fun quickSort(arr: IntArray, low: Int, high: Int) {
    var last = high
    var first = low
    //选取基准
    val standard = arr[first]

    while (first < last) {
        //把小于基准的放到左边
        while (first < last && arr[last] >= standard) {
            last--
        }
        arr[first] = arr[last]

        //把大于基准的放到右边
        while (first < last && arr[first] < standard) {
            first++
        }
        arr[last] = arr[first]
    }

    //基准归位
    arr[first] = standard

    if (first > low) quickSort(arr, low, first)
    if (first < high) quickSort(arr, first + 1, high)
}

//归并排序
fun mergeSort(arr: IntArray) {
    val temp = IntArray(arr.size) //在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
    mergeSort(arr, 0, arr.size - 1, temp)
}

private fun mergeSort(arr: IntArray, left: Int, right: Int, temp: IntArray) {
    if (left < right) {
        val mid = (left + right) / 2
        mergeSort(arr, left, mid, temp) //左边归并排序，使得左子序列有序
        mergeSort(arr, mid + 1, right, temp) //右边归并排序，使得右子序列有序
        merge(arr, left, mid, right, temp) //将两个有序子数组合并操作
    }
}

private fun merge(arr: IntArray, left: Int, mid: Int, right: Int, temp: IntArray) {
    var left = left
    var i = left //左序列指针
    var j = mid + 1 //右序列指针
    var t = 0 //临时数组指针
    while (i <= mid && j <= right) {
        if (arr[i] <= arr[j]) {
            temp[t++] = arr[i++]
        } else {
            temp[t++] = arr[j++]
        }
    }
    while (i <= mid) { //将左边剩余元素填充进temp中
        temp[t++] = arr[i++]
    }
    while (j <= right) { //将右序列剩余元素填充进temp中
        temp[t++] = arr[j++]
    }
    t = 0
    //将temp中的元素全部拷贝到原数组中
    while (left <= right) {
        arr[left++] = temp[t++]
    }
}


// 堆排序
fun heapSort(arr: IntArray) {
    val size = arr.size
    //自下而上构建大顶堆
    for (i in size / 2 - 1 downTo 0) {
        buildHeap(arr, i, size)
    }

    for (i in size - 1 downTo 1) {
        swap(arr, i, 0) //把最大的放到后面去
        buildHeap(arr, 0, i) // 自上而下重新构建大顶堆
    }
}

fun buildHeap(arr: IntArray, parent: Int, length: Int) {
    val left = parent * 2 + 1
    val right = parent * 2 + 2
    var needChange = parent

    //找到最大的子节点，换位置
    if (left < length && arr[left] > arr[needChange]) needChange = left
    if (right < length && arr[right] > arr[needChange]) needChange = right

    if (needChange != parent) {
        swap(arr, needChange, parent)

        //子节点有变化，重新排序
        buildHeap(arr, needChange, length)
    }
}

fun swap(arr: IntArray, i: Int, j: Int) {
    val temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}

// 4 寻找两个正序数组的中位数
fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
    val mutableListOf = mutableListOf<Int>()
    mutableListOf.addAll(nums1.toMutableList())
    mutableListOf.addAll(nums2.toMutableList())
    mutableListOf.sort()


    return if (mutableListOf.size % 2 == 1) {
        mutableListOf[mutableListOf.size / 2].toDouble()
    } else {
        (mutableListOf[mutableListOf.size / 2] + mutableListOf[mutableListOf.size / 2 - 1]).toDouble().div(2)
    }

}


//5 最长回文字符串
// 遍历，每个char为中心像两边扩散
fun longestPalindrome(s: String): String {

    var maxStr = ""
    for (i in s.indices) {
        val odd = palindrome(s, i, i)
        val even = palindrome(s, i, i + 1)

        maxStr = if (maxStr.length > odd.length) maxStr else odd
        maxStr = if (maxStr.length > even.length) maxStr else even

    }

    return maxStr

}

fun palindrome(str: String, left: Int, right: Int): String {
    var left1 = left
    var right1 = right
    while (left1 >= 0 && right1 < str.length && str[left1] == str[right1]) {
        left1--
        right1++
    }

    return str.substring(left1 + 1, right1)
}

fun maxArea(height: IntArray): Int {
    var left = 0
    var right = height.size - 1
    var res = 0
    while (left != right) {
        if (height[left] > height[right]) {
            right--

            res = max(res, height[right] * (right - left))
        } else {
            left++
            res = max(res, height[left] * (right - left))
        }

    }
    return res
}


//26 删除有序数组中的重复项
// slow 走后面，fast 走前面探路，找到一个不重复的元素就 让slow 前进一步 并让fast赋值给 slow 。
fun removeDuplicates(nums: IntArray): Int {
    if (nums.isEmpty()) return 0

    var slow = 0
    var fast = 1
    while (fast < nums.size) {
        if (nums[slow] != nums[fast]) {
            slow++
            nums[slow] = nums[fast]
        }

        fast++
    }

    return slow + 1
}

//27 移除元素 ，跟上面一样
fun removeElement(nums: IntArray, `val`: Int): Int {
    if (nums.isEmpty()) return 0

    var slow = 0
    var fast = 0
    while (fast < nums.size) {
        if (nums[fast] != `val`) {
            nums[slow] = nums[fast]
            slow++
        }
        fast++
    }

    return slow
}

//34 查排序数组元素边界
fun searchRange(nums: IntArray, target: Int): IntArray {
    if (nums.isEmpty()) return intArrayOf(-1, -1)
    return intArrayOf(findLeft(nums, target), findRight(nums, target))
}

fun findLeft(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        val mid = (left + right) / 2
        if (nums[mid] == target) {
            right = mid - 1
        } else if (nums[mid] < target) {
            left = mid + 1
        } else if (nums[mid] > target) {
            right = mid - 1
        }
    }

    if (left < 0 || left >= nums.size) return -1

    return if (target == nums[left]) left else -1
}

fun findRight(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1
    while (left <= right) {
        val mid = (left + right) / 2
        if (nums[mid] == target) {
            left = mid + 1
        } else if (nums[mid] < target) {
            left = mid + 1
        } else if (nums[mid] > target) {
            right = mid - 1
        }
    }

    if (right < 0 || right >= nums.size) return -1


    return if (nums[right] == target) right else -1
}


// 35 搜索插入位置
fun searchInsert(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        val mid = (left + right + 1) / 2

        if (nums[mid] == target) {
            return mid
        } else if (nums[mid] < target) {
            left = mid + 1
        } else {
            right = mid - 1
        }
    }

    return left

}

// 136 只出现一次的数字
fun singleNumber(nums: IntArray): Int {
    if (nums.size == 1) return nums[0]
    nums.sort()

    for (i in nums.indices step 2) {
        if (i + 1 == nums.size || nums[i] != nums[i + 1]) {
            return nums[i]
        }
    }

    return 0
}


//263 两数之和， 输入有序数组
fun twoSum(numbers: IntArray, target: Int): IntArray {
    var left = 0
    var right = numbers.size - 1
    while (left != right) {
        val temp = numbers[left] + numbers[right]
        if (temp > target) {
            right--
        } else if (temp < target) {
            left++
        } else {
            return intArrayOf(left + 1, right + 1)
        }
    }

    return IntArray(0)
}

// 283 移动零
fun moveZeroes(nums: IntArray): Unit {

    var fast = 0
    var slow = 0

    while (fast < nums.size) {
        if (nums[fast] != 0) {
            nums[slow] = nums[fast]
            slow++
        }
        fast++
    }

    while (slow < nums.size) {
        nums[slow] = 0
        slow++
    }
}

// 287 寻找重复数
fun findDuplicate(nums: IntArray): Int {
    nums.sort()
    var left = 0
    var right = nums.size - 1
    while (left <= right) {
        val mid = (left + right) / 2
        if (mid + 1 == nums[mid]) {
            left = mid + 1
        } else if (mid + 1 > nums[mid]) {
            right = mid - 1
        } else if (mid + 1 < nums[mid]) {
            left = mid + 1
        }
    }

    return left
}

//344 反转字符串
fun reverseString(s: CharArray): Unit {
    var left = 0
    var right = s.size - 1

    while (right > left) {
        val temp = s[left]
        s[left] = s[right]
        s[right] = temp
        left++
        right--
    }
    print(s)
}

fun main() {
    print(lengthOfLongestSubstring("sadsada"))
}
