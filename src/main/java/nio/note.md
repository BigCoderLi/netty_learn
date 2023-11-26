Nio 
Buffer: 
    1. capacity: 当前buffer容器中的元素数量, 此变量一但allocate()后, 就不会再被改变
    2. position： buffer中将要被读写的下一个元素的索引，它不会超过limit
    3. limit： buffer中要被读写的最后一个元素的下一个元素的索引, 它不会超过capacity