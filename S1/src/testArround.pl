#!/usr/bin/perl

#
# @File testArround.pl
# @Author uapv9906226
#

use POSIX;
my $number = 3.78;
my $intNumber = floor($number);
print("Number : $number \n");
print("NumberInt : $intNumber \n");

my $testNumber = $number - $intNumber;if((number%2)>0){
	if(ceil($testNumber)>0){
		$intNumber++;
	}else{
		$intNumber+=0.5;
	}}

print("Resultat : $intNumber \n");
