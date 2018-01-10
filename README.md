### 一、前言

已经好久没有更新博客，大概有半年了，主要是博主这段时间忙于找工作，Android岗位的工作真的是越来越难找，好不容易在广州找到一家，主要做海外产品，公司研发实力也不错，所以就敲定了三方协议。现在已经在公司实习了一个月多，目前主要是负责公司某个产品的内存优化，刚好就总结了一下Android Bitmap常用的优化方式。

Android中的图片是以Bitmap方式存在的，绘制的时候也是Bitmap，直接影响到app运行时的内存，在Android，Bitmap所占用的内存计算公式是：图片长度 x 图片宽度 x像素点的字节数

### 二、图片常用的压缩格式


Enum Values |    | 
---|---
ALPHA_8  | 每个像素都存储为一个半透明（alpha）通道
ARGB_4444 |此字段已在API级别13中弃用。由于此配置的质量较差，建议使用ARGB_8888
ARGB_8888   | 每个像素存储在4个字节。
RGB_565  | 每个像素存储在2个字节中，只有RGB通道被编码：红色以5位精度存储（32个可能值），绿色以6位精度存储（64个可能值），蓝色存储为5位精确。

其中字母代表的意思我们大概都可以理解，接下来我们来算算它们单个像素点的字节数：
- ALPHA_8：表示8位Alpha位图,即透明度占8个位,一个像素点占用1个字节,它没有颜色,只有透明度。
- ARGB_4444：表示16位ARGB位图，即A=4,R=4,G=4,B=4,一个像素点占4+4+4+4=16位，2个字节。
- ARGB_8888：表示32位ARGB位图，即A=8,R=8,G=8,B=8,一个像素点占8+8+8+8=32位，4个字节。
- RGB_565 ：表示16位RGB位图,即R=5,G=6,B=5,它没有透明度,一个像素点占5+6+5=16位，2个字节


我们在做压缩处理的时候，可以先通过改变Bitmap的图片格式，来达到压缩的效果，其实压缩最主要就是要么改变其宽高，要么就通过减少其单个像素占用的内存。

### 三、常用的压缩方法：

#### 1.质量压缩


```
    private void compressQuality() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mSrcSize = bm.getByteCount() + "byte";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        mSrcBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
```

质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度，来达到压缩图片的目的，图片的长，宽，像素都不会改变，那么bitmap所占内存大小是不会变的。

我们可以看到有个参数：quality，可以调节你压缩的比例，但是还要注意一点就是，质量压缩堆png格式这种图片没有作用，因为png是无损压缩。


#### 2.采样率压缩


```
    private void compressSampling() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
    }
```

采样率压缩其原理其实也是缩放bitamp的尺寸，通过调节其inSampleSize参数，比如调节为2，宽高会为原来的1/2，内存变回原来的1/4.


#### 3.放缩法压缩


```
    private void compressMatrix() {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mSrcBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        bm = null;
    }
```
放缩法压缩使用的是通过矩阵对图片进行裁剪，也是通过缩放图片尺寸，来达到压缩图片的效果，和采样率的原理一样。

#### 4.RGB_565压缩


```
    private void compressRGB565() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
    }
```


这是通过压缩像素占用的内存来达到压缩的效果，一般不建议使用ARGB_4444，因为画质实在是辣鸡，如果对透明度没有要求，建议可以改成RGB_565，相比ARGB_8888将节省一半的内存开销。


#### 5.createScaledBitmap


```
    private void compressScaleBitmap() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mSrcBitmap = Bitmap.createScaledBitmap(bm, 600, 900, true);
        bm = null;
    }
```

将图片的大小压缩成用户的期望大小，来减少占用内存。




### 四、总结

以上5种就是我们常用的压缩方法了，这里的压缩也只是针对在运行加载的bitmap占用内存的大小。我们在做App内存优化的时候，一般可以从这两个方面入手，一个内存泄漏，另外一个是Bitmap压缩了，在要求像素不高的情况下，可以对Bitmap进行压缩，并且针对一些只使用一次的bitmap，要做好recycle的处理。

博客就写到这里，以下是[源码地址。](https://github.com/codingma/BitmapCompress)
