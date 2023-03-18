# non-heap-video-streaming
Non Heap memory used as a storage for video streaming.
This program has been created as my input to the DAC.digital technological blog: 
https://dac.digital/netflix-on-java-a-non-heap-application-for-video-streaming/

#How to run?
A few ways:
1. run VideoStreamingApplication class (a main method).
2. Application will start at port 9000
3. Visit your localhost http://localhost:9000/



#Working with the application
You may upload and play a mp4 movie.
The video data will be places into Non-Heap memory, so it won't bother the JVM GC.
There is an internal scheduler which prints the memory usage both Heap and Non-Heap
For the testing purposes there is System.gc() used after each upload.

#Author
klaudiusz.wojtkowiak@gmail.com

The ByteBufferBackedInputStream class taken from:
https://stackoverflow.com/questions/4332264/wrapping-a-bytebuffer-with-an-inputstream/34173251#34173251