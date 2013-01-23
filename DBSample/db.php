<?php

// データベースの情報
$server = 'localhost';
$user = 'root';
$pass = $user;

// データベースと接続
$conn = mysql_connect($server,$user,$pass);
if(!$conn) die('Database Connection Error');

// データベース(スキーマ)の選択
$db = 'mydb';
$result = mysql_select_db($db,$conn);
if(!$result) die('Database Selection ERROR');

// SQLの実行
$table = 'mytable';
$query = 'SELECT * FROM '.$table;
$result = mysql_query($query,$conn);

// タプルの取り出し
while ($tuple = mysql_fetch_array($result)){
  echo '<p>'.$tuple['id'].':'.$tuple['text'].'</p>';
}

// データベースのクローズ
$conn = mysql_close($conn);

/*

CREATE  TABLE `mydb`.`table` (
  `id` INT NOT NULL ,
  `text` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );

*/