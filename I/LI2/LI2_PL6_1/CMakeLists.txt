cmake_minimum_required(VERSION 3.13)
project(proj C)

set(CMAKE_C_STANDARD 99)

add_executable(proj main.c estado.h estado.c interpretador.c interpretador.h jogada.c jogada.h undo.c undo.h bot.c bot.h)