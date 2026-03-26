package me.lining.learn.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
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
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lining
 * @date 2026/03/21 01:05
 */
public class MyBatisPlusGenerator {

    // 数据库连接配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/msrv_demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "456123";

    // 项目基础配置
    private static final String PROJECT_PATH = System.getProperty("user.dir"); // 项目根路径
    private static final String AUTHOR = "your_name"; // 作者名
    private static final String BASE_PACKAGE = "me.lining.learn"; // 基础包名
    private static final String XML_PATH = PROJECT_PATH + "/msrv-order/src/main/resources/mapper/"; // XML文件输出路径

    public static void main(String[] args) {
        // 1. 数据源配置
        AutoGenerator mpg = new AutoGenerator();

        // 2. 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/msrv-order/src/main/java"); // 代码输出根目录
        gc.setAuthor("your_name"); // 作者
        gc.setOpen(false); // 生成后是否打开文件夹
        gc.setFileOverride(true); // 覆盖已生成文件
        gc.setServiceName("%sService"); // Service命名规则（去掉默认的I前缀）
        gc.setMapperName("%sMapper"); // Mapper接口命名规则
        gc.setXmlName("%sMapper"); // XML文件命名规则
        gc.setEntityName("%s"); // 实体类命名规则
        mpg.setGlobalConfig(gc);

        // 3. 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver"); // MySQL8.x驱动
        // MySQL5.x驱动：dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("456123");
        mpg.setDataSource(dsc);

        // 4. 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("me.lining.learn"); // 基础包名
        pc.setEntity("entity"); // 实体类包
        pc.setMapper("mapper"); // Mapper接口包
        pc.setService("service"); // Service接口包
        pc.setServiceImpl("service.impl"); // Service实现包
        pc.setController("controller"); // Controller包
        mpg.setPackageInfo(pc);

        // 5. 自定义配置（核心：指定XML输出路径）
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可自定义参数，此处无需配置
            }
        };
        // XML模板路径（默认模板）
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义XML输出路径
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // XML文件输出路径：项目根目录/src/main/resources/mapper/表名对应的Mapper.xml
                return projectPath + "/msrv-order/src/main/resources/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 6. 模板配置（指定使用默认模板，启用XML生成）
        TemplateConfig templateConfig = new TemplateConfig();
        // 关闭默认XML生成（因为上面自定义了XML输出路径，避免重复生成）
        templateConfig.setXml(null);
        // 其他模板使用默认配置（Entity、Mapper、Service、Controller）
        mpg.setTemplate(templateConfig);

        // 7. 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 表名转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 字段名转驼峰
        strategy.setEntityLombokModel(true); // 开启Lombok
        strategy.setInclude("account_tbl"); // 生成的表名
        strategy.setTablePrefix("t_"); // 过滤表前缀（如t_user -> User）
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine()); // 模板引擎

        // 8. 执行生成
        mpg.execute();
        System.out.println("代码生成完成！XML文件路径：" + projectPath + "/msrv-order/src/main/resources/mapper/");
    }
}
