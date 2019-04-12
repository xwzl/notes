package com.java.spring.ioc;

/**
 * https://jiwenke.iteye.com/blog/493965
 *
 * <h2>IoC是什么</h2>
 * Ioc—Inversion of Control，即“控制反转”，不是什么技术，而是一种设计思想。在Java开发中，Ioc意味着将你设计好的对象交给容器控制，
 * 而不是传统的在你的对象内部直接控制。如何理解好Ioc呢？理解好Ioc的关键是要明确“谁控制谁，控制什么，为何是反转（有反转就应该有正转了），
 * 哪些方面反转了”，那我们来深入分析一下：
 * <ul>
 * <li><b>谁控制谁，控制什么</b>：传统Java SE程序设计，我们直接在对象内部通过new进行创建对象，是程序主动去创建依赖对象；而IoC是
 * 有专门一个容器来创建这些对象，即由Ioc容器来控制对象的创建；谁控制谁？当然是IoC 容器控制了对象；控制什么？那就是主要控制了外部资源
 * 获取（不只是对象包括比如文件等）。</li>
 * <li><b>为何是反转，哪些方面反转了</b>：有反转就有正转，传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而
 * 反转则是由容器来帮忙创建及注入依赖对象；为何是反转？因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些
 * 方面反转了？依赖对象的获取被反转了。</li>
 * <li></li>
 * </ul>
 * <h2>IoC能做什么</h2>
 * IoC不是一种技术，只是一种思想，一个重要的面向对象编程的法则，它能指导我们如何设计出松耦合、更优良的程序。传统应用程序都是由我们在类
 * 内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；有了IoC容器后，把创建和查找依赖对象的控制权交给了容器，由容器进行注入组合
 * 对象，所以对象与对象之间是松散耦合，这样也方便测试，利于功能复用，更重要的是使得程序的整个体系结构变得非常灵活。
 * <p>
 * 其实IoC对编程带来的最大改变不是从代码上，而是从思想上，发生了“主从换位”的变化。应用程序原本是老大，要获取什么资源都是主动出击，但是
 * 在IoC/DI思想中，应用程序就变成被动的了，被动的等待IoC容器来创建并注入它所需要的资源了。
 * <p>
 * IoC很好的体现了面向对象设计法则之一—— 好莱坞法则：“别找我们，我们找你”；即由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。
 * <h2>IoC和DI</h2>
 * DI—Dependency Injection，即“依赖注入”：是组件之间依赖关系由容器在运行期决定，形象的说，即由容器动态的将某个依赖关系注入到组件之中。
 * 依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通
 * 过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。
 * <p>
 * 理解DI的关键是：“谁依赖谁，为什么需要依赖，谁注入谁，注入了什么”，那我们来深入分析一下：
 * <ul>
 * <li><b>谁依赖于谁：</b>当然是应用程序依赖于IoC容器；</li>
 * <li><b>为什么需要依赖：</b>应用程序需要IoC容器来提供对象需要的外部资源；</li>
 * <li><b>谁注入谁：</b>很明显是IoC容器注入应用程序某个对象，应用程序依赖的对象；</li>
 * <li><b>注入了什么：</b>就是注入某个对象所需要的外部资源（包括对象、资源、常量数据）。</li>
 * </ul>
 * <p>
 * IoC和DI由什么关系呢？其实它们是同一个概念的不同角度描述，由于控制反转概念比较含糊（可能只是理解为容器控制对象这一个层面，很难让人想到谁来
 * 维护对象关系），所以2004年大师级人物Martin Fowler又给出了一个新的名字：“依赖注入”，相对IoC 而言，“依赖注入”明确描述了“被注入对象依赖
 * IoC容器配置依赖对象”。
 *
 * @author xuweizhi
 * @date 2019/03/19 13:24
 */
public class IoCDefine {

    /**
     * <h2>IoC理论的背景</h2>
     * 我们都知道，在采用面向对象方法设计的软件系统中，它的底层实现都是<b>由N个对象组成的，所有的对象通过彼此的合作，最终实现系统的业务逻辑。</b>
     * <p>
     * 如果我们打开机械式手表的后盖，就会看到与上面类似的情形，各个齿轮分别带动时针、分针和秒针顺时针旋转，从而在表盘上产生正确的时间。图1中描述的
     * 就是这样的一个齿轮组，它拥有多个独立的齿轮，这些齿轮相互啮合在一起，协同工作，共同完成某项任务。我们可以看到，在这样的齿轮组中，如果有一个齿
     * 轮出了问题，就可能会影响到整个齿轮组的正常运转。
     * <p>
     * 齿轮组中齿轮之间的啮合关系,与软件系统中对象之间的耦合关系非常相似。对象之间的耦合关系是无法避免的，也是必要的，这是协同工作的基础。现在，伴随
     * 着工业级应用的规模越来越庞大，对象之间的依赖关系也越来越复杂，经常会出现对象之间的多重依赖性关系，因此，架构师和设计师对于系统的分析和设计，将
     * 面临更大的挑战。对象之间耦合度过高的系统，必然会出现牵一发而动全身的情形。
     * <p>
     * 耦合关系不仅会出现在对象与对象之间，也会出现在软件系统的各模块之间，以及软件系统和硬件系统之间。如何降低系统之间、模块之间和对象之间的耦合度，是
     * 软件工程永远追求的目标之一。为了解决对象之间的耦合度过高的问题，软件专家Michael Mattson提出了IOC理论，用来实现对象之间的“解耦”，目前这个理论
     * 已经被成功地应用到实践当中，很多的J2EE项目均采用了IOC框架产品Spring。
     * <h2>控制反转</h2>
     * <p>
     * IOC是Inversion of Control的缩写，多数书籍翻译成“控制反转”，还有些书籍翻译成为“控制反向”或者“控制倒置”。
     * <p>
     * 1996年，Michael Mattson在一篇有关探讨面向对象框架的文章中，首先提出了IOC 这个概念。对于面向对象设计及编程的基本思想，前面我们已经讲了很多了，
     * 不再赘述，简单来说就是把复杂系统分解成相互合作的对象，这些对象类通过封装以后，内部实现对外部是透明的，从而降低了解决问题的复杂度，而且可以灵活地被
     * 重用和扩展。IOC理论提出的观点大体是这样的：借助于“第三方”实现具有依赖关系的对象之间的解耦，如下图：
     * <p>
     * 大家看到了吧，由于引进了中间位置的“第三方”，也就是IOC容器，使得A、B、C、D这4个对象没有了耦合关系，齿轮之间的传动全部依靠“第三方”了，全部对象的控
     * 制权全部上缴给“第三方”IOC容器，所以，IOC容器成了整个系统的关键核心，它起到了一种类似“粘合剂”的作用，把系统中的所有对象粘合在一起发挥作用，如果没
     * 有这个“粘合剂”，对象与对象之间会彼此失去联系，这就是有人把IOC容器比喻成“粘合剂”的由来。
     * <h2>IOC的别名：依赖注入(DI)</h2>
     * 2004年，Martin Fowler探讨了同一个问题，既然IOC是控制反转，那么到底是“哪些方面的控制被反转了呢？”，经过详细地分析和论证后，他得出了答案：“获得依赖对象
     * 的过程被反转了”。控制被反转之后，获得依赖对象的过程由自身管理变为了由IOC容器主动注入。于是，他给“控制反转”取了一个更合适的名字叫做“依赖注入（Dependency
     * Injection）”。他的这个答案，实际上给出了实现IOC的方法：注入。<b>所谓依赖注入，就是由IOC容器在运行期间，动态地将某种依赖关系注入到对象之中。</b>
     * <p>
     * 所以，依赖注入(DI)和控制反转(IOC)是从不同的角度的描述的同一件事情，就是指通过引入IOC容器，利用依赖关系注入的方式，实现对象之间的解耦。
     * <p>
     * 对象A依赖于对象B,当对象 A需要用到对象B的时候，IOC容器就会立即创建一个对象B送给对象A。IOC容器就是一个对象制造工厂，你需要什么，它会给你送去，你直
     * 接使用就行了，而再也不用去关心你所用的东西是如何制成的，也不用关心最后是怎么被销毁的，这一切全部由IOC容器包办。
     * <p>
     * 在传统的实现中，由程序内部代码来控制组件之间的关系。<b>我们经常使用new关键字来实现两个组件之间关系的组合，这种实现方式会造成组件之间耦合。IOC很好地解
     * 决了该问题，它将实现组件间关系从程序内部提到外部容器，也就是说由容器在运行期将组件间的某种依赖关系动态注入组件中。</b>
     * <h2>IOC为我们带来了什么好处</h2>
     * 我们还是从USB的例子说起，使用USB外部设备比使用内置硬盘，到底带来什么好处？
     * <p>
     * 第一、USB设备作为电脑主机的外部设备，在插入主机之前，与电脑主机没有任何的关系，只有被我们连接在一起之后，两者才发生联系，具有相关性。所以，无论两者中的任何一
     * 方出现什么的问题，都不会影响另一方的运行。这种特性体现在软件工程中，就是可维护性比较好，非常便于进行单元测试，便于调试程序和诊断故障。代码中的每一个Class都可
     * 以单独测试，彼此之间互不影响，只要保证自身的功能无误即可，这就是组件之间低耦合或者无耦合带来的好处。
     * <p>
     * 第二、USB设备和电脑主机的之间无关性，还带来了另外一个好处，生产USB设备的厂商和生产电脑主机的厂商完全可以是互不相干的人，各干各事，他们之间唯一需要遵守的就是
     * USB接口标准。这种特性体现在软件开发过程中，好处可是太大了。每个开发团队的成员都只需要关心实现自身的业务逻辑，完全不用去关心其它的人工作进展，因为你的任务跟别
     * 人没有任何关系，你的任务可以单独测试，你的任务也不用依赖于别人的组件，再也不用扯不清责任了。所以，在一个大中型项目中，团队成员分工明确、责任明晰，很容易将一个
     * 大的任务划分为细小的任务，开发效率和产品质量必将得到大幅度的提高。
     * <p>
     * 第三、同一个USB外部设备可以插接到任何支持USB的设备，可以插接到电脑主机，也可以插接到DV机，USB外部设备可以被反复利用。在软件工程中，这种特性就是可复用性好，我
     * 们可以把具有普遍性的常用组件独立出来，反复利用到项目中的其它部分，或者是其它项目，当然这也是面向对象的基本特征。显然，IOC不仅更好地贯彻了这个原则，提高了模块的
     * 可复用性。符合接口标准的实现，都可以插接到支持此标准的模块中。
     * <p>
     * 第四、同USB外部设备一样，模块具有热插拔特性。IOC生成对象的方式转为外置方式，也就是把对象生成放在配置文件里进行定义，这样，当我们更换一个实现子类将会变得很简单，
     * 只要修改配置文件就可以了，完全具有热插拨的特性。
     * <p>
     * 以上几点好处，难道还不足以打动我们，让我们在项目开发过程中使用IOC框架吗？
     * <h2>IOC容器的技术剖析</h2>
     * <p>
     * IOC中最基本的技术就是“反射(Reflection)”编程，目前.Net C#、Java和PHP5等语言均支持，其中PHP5的技术书籍中，有时候也被翻译成“映射”。有关反射的概念和用法，
     * 大家应该都很清楚，通俗来讲就是根据给出的类名（字符串方式）来动态地生成对象。这种编程方式可以让对象在生成时才决定到底是哪一种对象。反射的应用是很广泛的，很多的
     * 成熟的框架，比如象Java中的Hibernate、Spring框架，.Net中 NHibernate、Spring.Net框架都是把“反射”做为最基本的技术手段。
     * <p>
     * 反射技术其实很早就出现了，但一直被忽略，没有被进一步的利用。当时的反射编程方式相对于正常的对象生成方式要慢至少得10倍。现在的反射技术经过改良优化，已经非常成熟，
     * <b>反射方式生成对象和通常对象生成方式，速度已经相差不大了，大约为1-2倍的差距。</b>
     * <p>
     * <b>我们可以把IOC容器的工作模式看做是工厂模式的升华，可以把IOC容器看作是一个工厂，这个工厂里要生产的对象都在配置文件中给出定义，然后利用编程语言的的反射编程，根据
     * 配置文件中给出的类名生成相应的对象。从实现来看，IOC是把以前在工厂方法里写死的对象生成代码，改变为由配置文件来定义，也就是把工厂和对象生成这两者独立分隔开来，目
     * 的就是提高灵活性和可维护性。</b>
     */
    public static void main(String[] args) {
        //传统方式
        IoCDefine ioc = new IoCDefine();

        //IOC 有容器来帮我们注入对象；如何理解翻转呢？容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转
        //哪些方面翻转呢？依赖对象的获取被反转了。
        //我对SpringIoC的理解：运用Spring框架，把对象的创建权交给了SpringIoC容器，这就是SpringIoC。当我们需要对象的时候
        //不需要关注创建对象的细节，只要关注谁依赖谁，为什么需要依赖，谁注入谁，注入了什么即可，也就对IoC另外一种阐述，需要某个
        //对象，通过IoC容器创建对象，实现注入即可。
        //ClassPathXmlApplicationContext();
    }



}
