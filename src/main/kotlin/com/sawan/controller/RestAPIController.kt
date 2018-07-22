package com.sawan.controller

import com.sawan.model.ModelMp3
import com.sawan.model.ResponseModel
import com.sawan.repository.Mp3Repository
import com.sawan.utils.Constant
import com.sawan.utils.Constant.emptyArrays
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.ArrayList

import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import sun.security.krb5.internal.Ticket


@RestController
@RequestMapping(value = ["/api"])
class RestAPIController {

    var countRequest=0

    private val UPLOADED_FOLDER_IMG = "E:\\Spring\\Sawan\\multimedia\\images\\"
    private val UPLOADED_FOLDER_MP3 = "E:\\Spring\\Sawan\\multimedia\\mp3\\"
    private val UPLOADED_FOLDER_VIDEO = "E:\\Spring\\Sawan\\multimedia\\video\\"
    private val UPLOADED_PDF = "E:\\Spring\\Sawan\\multimedia\\pdf\\"


    @Autowired
    internal var customerRepository: Mp3Repository? = null


    @Throws(Exception::class)
    private fun generatePdf(): Document {
        val document = Document()

        PdfWriter.getInstance(document, FileOutputStream("$UPLOADED_PDF/output.pdf"))
        document.open()
        val table = PdfPTable(2)

        val cell = PdfPCell(Paragraph("Ticket"))

        cell.colspan = 2
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setPadding(10.0f)
        cell.backgroundColor = BaseColor(140, 221, 8)

        table.addCell(cell)
        val row = ArrayList<Array<String?>>()
        var data = arrayOfNulls<String>(2)
        data[0] = "Ticket number:"
        data[1] = "126655878"
        row.add(data)

        data = arrayOfNulls(2)
        data[0] = "Price:"
        data[1] = "567" + " PLN"
        row.add(data)

        data = arrayOfNulls(2)
        data[0] = "From:"
        data[1] = "Noida Sec 63"
        row.add(data)

        data = arrayOfNulls(2)
        data[0] = "To:"
        data[1] = "Pune"
        row.add(data)

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

        data = arrayOfNulls(2)
        data[0] = "Departure date:"
        data[1] = "2018-05-13 12:17"
        row.add(data)

        data = arrayOfNulls(2)
        data[0] = "Arrival date:"
        data[1] = "2018-05-11 1:49"
        row.add(data)

        for (i in row.indices) {
            val cols = row[i]
            for (j in cols.indices) {

                table.addCell(cols[j])

            }

        }

        document.add(table)
        document.close()
        return document
    }







    //create profile
    @PostMapping("/upload_mp3",consumes = ["multipart/form-data"])
    fun singleFileUploadMP3(@RequestParam("file") file: MultipartFile,
                         @RequestParam("name") name :String): Any {
        if (file.isEmpty) {
            return ResponseEntity(ResponseModel(false, Constant.shopAvatar, emptyArrays), Constant.NOT_FOUND)
        }

        val avatarPath=file.originalFilename
        try {


            val path = File(UPLOADED_FOLDER_MP3 + avatarPath)
            val bytes = file.bytes
            val stream = BufferedOutputStream(FileOutputStream(path))
            stream.write(bytes)
            stream.close()

            // customerRepository!!.save(RegistationModel(id,name,location,"http://localhost:8080/api/images/"+avatarPath!!))
            customerRepository!!.save(ModelMp3(name,avatarPath!!))
            return ResponseEntity(ResponseModel(true, Constant.regOK, emptyArrays), Constant.CREATED)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }


    //Get Image Url
    @GetMapping("/mp3/{path}")
    @ResponseBody
    @Throws(IOException::class)
    fun getMp3(@PathVariable ("path") path:String): ResponseEntity<ByteArray> {

       println("\n PDF Genrate \n "+ generatePdf()+"\n")

        val imgPath = File(UPLOADED_FOLDER_MP3+path)
        val image = Files.readAllBytes(imgPath.toPath())
        val headers = HttpHeaders()
        headers.contentType = MediaType("audio", "mp3")
        headers.contentLength = image.size.toLong()
        return ResponseEntity(image, headers, HttpStatus.OK)
    }





    //create Video
    @PostMapping("/upload_video",consumes = ["multipart/form-data"])
    fun singleFileUploadVideo(@RequestParam("file") file: MultipartFile,
                         @RequestParam("name") name :String): Any {
        if (file.isEmpty) {
            return ResponseEntity(ResponseModel(false, Constant.shopAvatar, emptyArrays), Constant.NOT_FOUND)
        }

        val avatarPath=file.originalFilename
        try {


            val path = File(UPLOADED_FOLDER_VIDEO + avatarPath)
            val bytes = file.bytes
            val stream = BufferedOutputStream(FileOutputStream(path))
            stream.write(bytes)
            stream.close()

            // customerRepository!!.save(RegistationModel(id,name,location,"http://localhost:8080/api/images/"+avatarPath!!))
            customerRepository!!.save(ModelMp3(name,avatarPath!!))
            return ResponseEntity(ResponseModel(true, Constant.regOK, emptyArrays), Constant.CREATED)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }


    //Get Video Url
    @GetMapping("/video/{path}")
    @ResponseBody
    @Throws(IOException::class)
    @Synchronized
    fun getVideo(@PathVariable ("path") path:String): ResponseEntity<ByteArray> {
        countRequest++
        println(" \n \n  Count Request :  $countRequest \n \n ")
        val imgPath = File(UPLOADED_FOLDER_VIDEO+path)
        val image = Files.readAllBytes(imgPath.toPath())
        val headers = HttpHeaders()
        headers.contentType = MediaType("video", "mp4")
        headers.contentLength = image.size.toLong()
        return ResponseEntity(image, headers, HttpStatus.OK)
    }













    //create profile
    @PostMapping("/upload",consumes = ["multipart/form-data"])
    fun singleFileUpload(@RequestParam("file") file: MultipartFile,
                         @RequestParam("name") name :String): Any {
        if (file.isEmpty) {
            return ResponseEntity(ResponseModel(false, Constant.shopAvatar, emptyArrays), Constant.NOT_FOUND)
        }

        val avatarPath=file.originalFilename
        try {
            val bytes = file.bytes
            val path = Paths.get(UPLOADED_FOLDER_IMG + avatarPath)
            Files.write(path, bytes)
            // customerRepository!!.save(RegistationModel(id,name,location,"http://localhost:8080/api/images/"+avatarPath!!))
            customerRepository!!.save(ModelMp3(name,avatarPath!!))
            return ResponseEntity(ResponseModel(true, Constant.regOK, emptyArrays), Constant.CREATED)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }


    //Get Image Url
    @GetMapping("/images/{path}")
    @ResponseBody
    @Throws(IOException::class)
    fun getPhoto(@PathVariable ("path") path:String): ResponseEntity<ByteArray> {
        val imgPath = File(UPLOADED_FOLDER_IMG+path)
        val image = Files.readAllBytes(imgPath.toPath())
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_JPEG
        headers.contentLength = image.size.toLong()
        return ResponseEntity(image, headers, HttpStatus.OK)
    }


}

