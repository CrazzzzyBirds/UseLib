cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)
# set()用来设置一个路径全局变量 distribution_DIR
set(distribution_DIR ${CMAKE_SOURCE_DIR}/../libBuild)

#创建总的库native-lib并链接一个.c文件，可以直接引用第三方库的头文件
add_library(native-lib SHARED
            ${CMAKE_SOURCE_DIR}/src/main/cpp/native-lib.c)
#链接第三方库的头文件
target_include_directories(native-lib PRIVATE
                           ${distribution_DIR}/one/include
                           ${distribution_DIR}/two/include)

# 创建一个静态库 lib_one 直接引用lib_a.a
add_library(lib_a STATIC IMPORTED)
set_target_properties(lib_a PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/one/lib/${ANDROID_ABI}/liblib_a.a)

# 创建一个动态库 lib_two 直接引用lib_so.so
add_library(lib_so SHARED IMPORTED)
set_target_properties(lib_so PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/two/lib/${ANDROID_ABI}/liblib_so.so)


find_library( log-lib
              log )

#把所有库文件都引入工程
target_link_libraries(
                       native-lib
                       lib_a
                       lib_so
                       ${log-lib} )