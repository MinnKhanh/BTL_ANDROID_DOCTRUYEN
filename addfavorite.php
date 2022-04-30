<?php
   $connect=mysqli_connect('localhost','root','','doctruyen');
   mysqli_set_charset($connect,'utf8');
   $array=[];
   $taikhoan=$_GET["taikhoan"];
   $matkhau=$_GET["matkhau"];
   $matruyen=$_GET["idtruyen"];
   $sql="SELECT id from taikhoan WHERE TaiKhoan = '$taikhoan' and MatKhau = '$matkhau'";
   $result=mysqli_query($connect,$sql);
   $each=mysqli_fetch_array($result);
   $manguoidung=$each["id"];
   $sql="SELECT count(*) FROM truyenyeuthich WHERE Matruyen=$matruyen and Manguoidung=$manguoidung";
   $result=mysqli_query($connect,$sql);
   $number_rows=mysqli_fetch_array($result)['count(*)'];
   if($number_rows>=1){
    header('Content-Type: application/json');
    echo json_encode(0);
   }else{
          $sql="INSERT INTO truyenyeuthich(Matruyen, Manguoidung) VALUES ($matruyen,$manguoidung)";
   $result=mysqli_query($connect,$sql);
   header('Content-Type: application/json');
   echo json_encode(1);
   }
