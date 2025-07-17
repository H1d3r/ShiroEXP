package com.y5neko.shiroexp.ui.common;

import com.y5neko.shiroexp.ui.tabpane.Shiro550Tab;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Center {
    public VBox getCenterBox(){
        VBox centerBox = new VBox();
        centerBox.getStylesheets().add("css/TextField.css");
        centerBox.setPadding(new Insets(10, 10, 10, 10));
        centerBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));


        // ==================================================创建标签页========================================================
        TabPane tabPane = new TabPane();
        tabPane.setTabMaxHeight(17);

        // Shiro550标签页
        Tab shiro550Tab = new Tab("Shiro550");
        shiro550Tab.setContent(new Shiro550Tab().getShiro550Tab());

        // Shiro721标签页
        Tab shiro721Tab = new Tab("Shiro721");
        shiro721Tab.setContent(new StackPane(new Label("Content 2")));

        // 漏洞利用标签页
        Tab exploitTab = new Tab("漏洞利用");
        exploitTab.setContent(new StackPane(new Label("漏洞利用")));

        // 设置标签页
        Tab settingsTab = new Tab("设置");
        settingsTab.setContent(new StackPane(new Label("设置")));

        // 添加到 TabPane
        tabPane.getTabs().addAll(shiro550Tab, shiro721Tab, exploitTab, settingsTab);
        // 禁止关闭标签
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        // 设置默认选中第一个标签页
        tabPane.getSelectionModel().select(0);
        // 设置标签页高度为自动适应内容
        VBox.setVgrow(tabPane, javafx.scene.layout.Priority.ALWAYS);
        centerBox.getChildren().add(tabPane);

        // ===============================================================================
        return centerBox;
    }
}
