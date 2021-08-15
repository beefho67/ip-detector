1. Import the Project with Eclipse
2. In the Project Explorer, right click the imported project.
3. Choose Run As > Maven build...
4. Enter package shade:shade in Goals. Hit Apply then hit Run.
5. Maven should trigger the build of the project.
6. The output jar can be found in the target folder.
7. Upload the jar to the corresponding Lambda Function using AWS console. 