---
date: 2020/03/12 10:47:18
title: HashMap
---
## 加载因子设置

1. 作用

1. 如何确定

## 扩容

1. 发生时期:

1. 如何实现

1. 为什么一定是2的倍数

## 其他遇到的点

1. transient 关键字修饰变量

    1. 意义
    
        添加了transient关键字的类的属性,在序列化它的对象的时候不会把这个属性序列化,即这个字段的生命周期仅仅在于内存中,不会持久化到磁盘中.
        
        疑问:那数据库呢?

    1. 用途
    
        [例子](https://www.cnblogs.com/lanxuezaipiao/p/3369962.html):
        
        例如一个用户类,有用户名和密码,我们序列化的时候,如果只需要序列化用户名,不想序列化用户的密码,那么就可以用 transient 修饰密码属性,这样序列化然后再反序列化之后得到的用户名正常,而密码为 null
        
    1. 值得注意的地方:
    
        1. 序列化仅仅是将对象转化成字符序列,只要调用 writeObject方法就能实现序列化,并且,序列化是基于**字节**的,不能使用所有继承 Writer类的方法(基于字符)
        
        1. 一个类的 static 变量,无论是否被 transient 修饰,都不可序列化
        
        1. 注意之前所说的序列化都是通过实现 Serializable 接口的,而特殊的,还有一种序列化方式是实现 Externalizable 接口
        
            第一种方式是需要 `className implement Serializable`即可,不需要重写任何方法,Serializable 接口本身就没有方法
            
            第二种方式除了实现 Externalizable 接口,还要重写 writerExternal 和 readExternal方法
            
        1. 如果是选择实现实现 Externalizable 方法,那么 transient 修饰符会失效!
        
            这是因为第一种方法则是可以自动序列化,而在重写 writeExternal 方法的时候,不仅要把对象通过流输出,在此之前还要自己手动序列化:自己实现序列化的细节!
            
        1. 并且,因为 Externalizable 实现了 Serializable 接口,所以前者的优先级要高于后者
        
        1. 通过实现 Serializable 接口的类不需要构造器来反序列化,而 Externalizable 接口的类需要默认的构造器
        
            对于Serializable对象，对象完全以它存储的二进制位作为基础来构造，而不调用构造器。
            
        1. Serializable 的 serialVersionUID,为了确定当前的序列化版本,防止之前已经写入对象之后修改了类,可能会导致反序列化时的信息丢失,加入这个 serialVersionUID 强制使得序列化的版本对应正确

1. @serial 注解

    1. 意义

    2. 用途

