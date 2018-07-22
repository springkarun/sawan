package com.sawan.utils

import org.springframework.http.HttpStatus


object Constant{



   const val regOK="Registation is create successfully."
   const val category="Category is create successfully."
   const val regExists="Roll No already exists !!."
   const val dataEmpty="Data is Empty."
   const val shopAvatar="Please select a shopAvatar to upload."

         val OK= HttpStatus.OK
         val CREATED= HttpStatus.CREATED
         val CONFLICT= HttpStatus.CONFLICT
         val NOT_FOUND= HttpStatus.NOT_FOUND
         val emptyArrays=  arrayOf<String>()


   /* 200 - SUCESS
    404 - RESOURCE NOT FOUND
    400 - BAD REQUEST
    201 - CREATED
    401 - UNAUTHORIZED
    415 - UNSUPPORTED TYPE - Representation not supported for the resource
    500 - SERVER ERROR*/




}