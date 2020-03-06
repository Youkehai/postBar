package com.example.demo.config.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MysqlGenerator {
	 /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //outputDir：生成文件的输出目录，默认值：D 盘根目录
        gc.setOutputDir(projectPath + "/src/main/java");
        //IdType设置主键生成策略，默认值null
        gc.setIdType(IdType.UUID);
        //Author设置作者
        gc.setAuthor("youkehai");
        //Open：是否打开输出目录
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://120.78.136.100:3306/myPostBar?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("MySql123");
        //设置数据库类型，默认MYSQL
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent("com.example.demo.myPostBar");
        mpg.setPackageInfo(pc);

        //模板配置


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //设置命名格式
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(false);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名"));
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //表名前缀
        strategy.setTablePrefix(pc.getModuleName() + "_");
        //是否生成实体时，生成字段注解
        strategy.entityTableFieldAnnotationEnable(true);
        //设置自定义继承的Entity类全称，带包名
//       strategy.setSuperEntityClass("com.lj.common.BaseEntit");
        //设置自定义继承的Controller类全称，带包名
//        strategy.setSuperControllerClass("com.lj.common.BaseController");
        //设置自定义基础的Entity类，公共字段
//        strategy.setSuperEntityColumns("id");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
