# BC

BC stands for Backward Chaining

## 官方文档内容

我自己翻译的

### 逆向推理

逆向规则推理系统是目标驱动的。意思是，推理系统以引擎满足的结论开始。如果找不到这个满足的结论，就去寻找代替的目标，即，结论是当前目标完整的一部分。持续这一过程，直到任何一个初始结论满足，或直到不再有不满足的代替目标了。Prolog就是一个逆向推理引擎的例子。

逆向推理过程图：

![Backward_Chaining](img/Backward_Chaining.png)

### 逆向推理系统

逆向推理是最近被加入JBoss规则引擎的一个功能。这个过程经常适用于查询导出，并且通常不会把它和反应式系统比较，因为JBoss 规则系统主要是反应式正向推理。意思是，它会对改变数据做出良好的响应。逆向推理更像是产品的衍生功能

## 网上的其他信息

* [How to use Drools backward chaining to list what initial facts are needed to satisfy a goal?](http://stackoverflow.com/questions/28010926/how-to-use-drools-backward-chaining-to-list-what-initial-facts-are-needed-to-sat)

**问题**

I'm trying to use Drools backward chaining to find out which facts are needed to get an object inserted in the working memory. In the following example, I expect to get the fact "go2".

	rule "ins a"
	when
	    String( this == "go2" )
	then 
	    insert(new A());
	end
	
	
	rule "Run"
	when
	then
	    insert(new String("go1"));
	end
	
	rule "Test isThereAnyA"
	    when
	        String( this == "go1" )
	        isThereAnyA(a;)
	    then
	        System.out.println( "you can get " + a );   
	end
	
	query isThereAnyA (A a)
	    a := A()
	end
	
I've been looking at examples in the official documentation http://docs.jboss.org/drools/release/6.1.0.Final/drools-docs/html_single/index.html#d0e21289 but they show a different situation (the rules in those examples doesn't creates new fact)

From the chart http://docs.jboss.org/drools/release/6.1.0.Final/drools-docs/html_single/index.html#d0e21240 I think it should work but I haven't found a way to specify a query that gives me the expected results.

Thank you in advance.

**下面贴出回答**

Short answer:

Unfortunately backward chaining can not be used for this purpose. It will not give you "go2" in this case.

简言之

很不幸，逆向推理不是用于这个目的的。你不会得到 go2

Long answer:

In Drools, Backward chaining (BC) is a way to query the WM in a goal-driven fashion, not a way to trace back the derivation graph of a normal forward chaining inference process.

详细说明：

Drools 中，逆向推理链是一个通过目标驱动的改变查询 WM 的方法，不是一个追溯正向推理链过程图的方法。

BC allows rule "Test" to retrieve As through the query "isThereAnyA", and possibly invoke other queries, but will not allow to find the "production" link between "A" and "go2". The reason is that "when..then..insert.." does not create any link between the triggering facts and the asserted conclusion, and backward chaining will not change it.



What you could do with BC is this:

	query isThereAnyA_InPresenceOfA_String( A a )
	   isThereAnyString( $s ; )       
	   a := A()
	end 
	query isThereAnyString( String $s )
	   $s := String( this == "go2" )
	end

This will pick up As only if a String "go2" is (still) present. However you'll notice that the connection between a particular instance of A and a the particular String which led to its assertion is still missing.

To know exactly which objects led to the assertion of another object you may need a different approach. Options include:

* make the connection explicit : new A( $s ) // $s bound to "go2"
* use "insertLogical" to establish a dependency between "go2" and A, then query the TruthMaintenanceSystem

The TMS-based one would be my tentative choice, but it also depends on your exact requirements.

This use case is common, there may be other options, including a few which are experimental as they are being developed in 6.3, but I'd rather ask a few questions first. That is: when do you need exactly to discover the facts - during the execution of the rules, or "offline"? Is it purely for auditing purposes, or does it impact your business logic? Can you have multiple rules asserting the "same" object?

Hope this helps Davide

