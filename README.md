# PreProcessorJ

My attempt to add a preprocessor to java

End files with .javar to use

Use #define to create macros

#define -what you want to write- -what the code will see-
For instance
  #define print System.out.println
  print("Hello World");
Will evaluate to
  System.out.println("Hello World");
For multiline values(multiline keys not currently supported) wrap value in parentheses
  #define multiline {
  something
  something else
  }
  multiline
Will evaluate to
  something
  something else
Use #mode debug -(true) or (false)- to switch between debug modes
When debug is true anything with #debug wrapped in braces will be evaluated, if false it will be skipped
debug is false by default
