#RXLIST
### 由来
每次写列表都会很烦躁，有没有，需要处理好多东西，会很烦，列表的唯一区别也就是内容，如何只关注内容，将重复的内容封装起来，只关注业务逻辑，Rx的方式是一个很好的方式，通过Rx将数据和显示内容剥离出来，于是就有了RxList。



### 如何导入
step1:在project build.gradle 文件中添加如下仓库
<pre>
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
</pre>
step2:￼在项目module中build.gradle 添加如下引用(如果是多module 的情况下，在不同的module下同样添加以下引入)
<pre>
	dependencies {
	        compile 'com.github.xyzmst:RxList:1.0'
	}
</pre>
	
### 如何使用
参照demo
##### 主要使用的类
- SRxListFragmentBind
- SRxSubscriber
- RxSimpleViewHolder
- SimpleView
- RxLinearListFragment
- RxSGrid2ListFragment
 
#### 方便的使用的工具类
- RxListActivity
- RxSGridActivityDemo

#### 切换
每次切换列表和表格只需要切换 Fragment或者继承的 Activity
### 框架图

### 构架思考


#### 下一步升级计划
- 解耦 RxListFragment
- 单独封装 Recyclerview
- 常用控件库
- Android Studio插件
- 支持kotlin 

 



## Licence

```
Copyright 2017 xyzmst

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```