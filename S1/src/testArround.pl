#!/usr/bin/perl

#
# @File testArround.pl
# @Author uapv9906226
#



my $intNumber = floor($number);

print("NumberInt : $intNumber \n");

my $testNumber = $number - $intNumber;
	if(ceil($testNumber)>0){
		$intNumber++;
	}else{
		$intNumber+=0.5;
	}

