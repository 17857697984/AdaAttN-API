# AdaAttN-api-
本项目针对AdaAttN模型，基于spring boot设计开发了调用该模型的api，供大家学习参考。
# api设计步骤
## 1.将需要进行风格变换的图片移动到指定的工作目录当中
## 2.运行cmd命令使用模型进行风格迁移
## 3.在模型结果目录下找到对应的结果图片，将其移动到图片存放目录
api.java文件遵循以上步骤进行了api的开发。

AdaAttN模型代码位于：https://github.com/Huage001/AdaAttN
