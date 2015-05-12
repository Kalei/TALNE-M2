#!/usr/bin/perl

#
# @File Q1.pl
# @Author uapv9906226
#

use strict;

my $file = "AppVk13.txt";
my @note;
my @date;
my @top_nb_critique=0;
my %hash = (
  'Film',
  'nb_critiques'
);

open my $info, $file or die "Could not open $file: $!";

while( my $line = <$info>)  {   
    my($id, $pseudo, $date, $titre, $note, $microCritique) = split /\t/,$line;
}

#Dislplay all notes
#foreach my $value (@note){
#    print ("$value\n");
#}


close $info;