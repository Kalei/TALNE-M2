    #!/usr/bin/perl

#
# @File Q1.pl
# @Author uapv9906226
#

use strict;

my $file = "AppVk13.txt";
my @note;
my @date;
my $total = 0;
my $nb_notes = 0;
my $moyenne = 0;
my $neg_note = 0;
my $pos_note = 0;

open my $info, $file or die "Could not open $file: $!";

while( my $line = <$info>)  {   
    my($id, $pseudo, $date, $titre, $note, $microCritique) = split /\t/,$line;

    if($note!=0){
        push @date,$date;
        push @note,$note;
        $total = $note + $total;

        if($note<=2.5){
            $neg_note++;
        }
        elsif($note>3.5){
            $pos_note++;
        }
        $nb_notes++;
    }
}

$moyenne = $total/$nb_notes;

#Dislplay all notes
#foreach my $value (@note){
#    print ("$value\n");
#}

my $pctMoyennePos = ($pos_note/$nb_notes) * 100;
my $pctMoyenneNeg = ($neg_note/$nb_notes) * 100;

print ("------Moyenne------\n");
print ("$moyenne\n");
print ("------Notes positives------\n");
print ("$pos_note sur $nb_notes notes soit $pctMoyennePos\n");
print ("------Notes negatives------\n");
print ("$neg_note sur $nb_notes notes soit $pctMoyenneNeg\n");

close $info;