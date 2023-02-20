package com.mashibing.serviceorder.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 *
 * @author xcy
 * @date 2023/2/16 - 20:51
 */
public class MySQLGenerator {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/service-order?characterEncoding=utf-8&serverTimezone=GMT%2B8";
		String username = "root";
		String password = "root";

		//全局配置
		String author = "xuchuangye";
		String outputJavaDir = "I:\\online-taxi-public\\service-order\\src\\main\\java";

		//包配置
		String parent = "com.mashibing.serviceorder";
		String outputMapperDir = "I:\\online-taxi-public\\service-order\\src\\main\\java\\com\\mashibing\\serviceorder\\mapper";

		//策略配置
		String tableName = "order_info";
		FastAutoGenerator.create(url, username, password)
				//全局配置
				.globalConfig(builder -> {
					builder.author(author)
							//如果有则覆盖
							.fileOverride()
							//输出到java目录中
							.outputDir(outputJavaDir);
				})
				//包配置
				.packageConfig(builder -> {
					//当前类所在的父包
					builder.parent(parent)
							//输出文件类型：mapperXml
							//输出到mapper目录中
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml, outputMapperDir));
				})
				//策略配置
				.strategyConfig(builder -> {
					//表名
					builder.addInclude(tableName);
					//过滤表名前缀
					//.addTablePrefix()
					//过滤表名后缀
					//.addTableSuffix()
				})
				//模板引擎
				.templateEngine(new FreemarkerTemplateEngine())
				//执行
				.execute();
	}
}
