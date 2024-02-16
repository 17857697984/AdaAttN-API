public String AdaAttnApi(String imageId,String style){
        LambdaQueryWrapper<MyFodder> lqw = new LambdaQueryWrapper<MyFodder>().eq(MyFodder::getId,imageId);
        MyFodder myFodder = myFodderDao.selectOne(lqw);
        String sourcePath = "***";
        String targetPath = "***";
        String fileName = ImageUtil.deleteURLPath(myFodder.getSrc());
        String suffix0 = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = System.currentTimeMillis()+"";
        try {
            copySingleImage(sourcePath,targetPath,fileName,newFileName + suffix0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String command = "e: & cd *** & " +
                "python test.py --content_path datasets\\contents --style_path datasets\\styles " +
                "--name AdaAttN --model adaattn --dataset_mode unaligned --load_size 512 --crop_size 512 " +
                "--image_encoder_path checkpoints\\vgg_normalised.pth --gpu_ids 0 --skip_connection_3 --shallow_layer";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", command);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("External command executed successfully");
            } else {
                System.out.println("External command execution failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String createImageName = style + "_" + newFileName +"_cs" + ".png";
        targetPath = sourcePath;
        sourcePath = "***";
        String newCreateName = System.currentTimeMillis() + "";
        try {
            copySingleImage(sourcePath,targetPath,createImageName,newCreateName+".png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "http://localhost:8085/img/"+newCreateName+".png";
    }
    private void copySingleImage(String sourceDirectoryPath, String targetDirectoryPath, String imageName,String newImageName) throws IOException {
        File sourceDirectory = new File(sourceDirectoryPath);
        File targetDirectory = new File(targetDirectoryPath);
      
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
      
        File sourceImage = new File(sourceDirectory, imageName);
        File targetImage = new File(targetDirectory, newImageName);
      
        copyFile(sourceImage, targetImage);
    }
    private void copyFile(File sourceFile, File targetFile) throws IOException {
        java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath());
    }
