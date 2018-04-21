# Android-Application-Development
The assignment and the project of the course Android Application Development

1. **基本 UI 界面设计**

   - 实现一个 Android 应用，界面呈现如下效果：

     ![](https://github.com/Cocaice/Android-Application/blob/master/page/1524111005(1).jpg)


   - 使用的布局和控件： ConstraintLayout、 TextView、 EditText、 Button、 ImageView、 RadioGroup、RadioButton

2. **事件处理**

   - 点击图片能够弹出对话框，并且显示相关Toast信息

   - 点击登录按钮能够判断学号密码并且显示相关提示，具体为

     ![](https://github.com/Cocaice/Android-Application/blob/master/page/hw2.jpg)

3. **Intent、Bundle的使用以及RecyclerView、ListView的应用**

   - 实验模拟一个商品表，其中一个界面有两个列表，一个用于呈现商品要求RecyclerView实现。

   - 另一个列表用于呈现购物车，通过悬浮按钮切换，要求ListView实现，且能够对该页面数据项进行删除

   - 点开任意一个列表的数据项可以进入商品详情页面，具体为

     ![](https://github.com/Cocaice/Android-Application/blob/master/page/hw3.jpg)

4. **Broadcast 使用**

   - 在实验三基础上，实现静态广播和动态广播两种改变Notification 内容的方法

   - 启动应用产生通知并随机推荐一个商品，然后点击通知进入商品详情页面

   - 点击添加购物车产生通知，并通过Eventbus在购物车列表更新数据，点击通知进入购物车页面

   - 启动页面的通知由静态广播产生，点击购物车图标的通知由动态广播产生，具体为

     ![](https://github.com/Cocaice/Android-Application/blob/master/page/hw4.jpg)


