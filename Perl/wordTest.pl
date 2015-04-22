#!D:\Dwimperl\perl\bin\perl.exe

use strict;
use warnings;
use IO::File;
use File::Basename;

my %sequences;
my %dupes;
my @inputs; #Temp test array
my $wordCount = 0;

my $filename = $ARGV[0];
open(my $fh, '<:encoding(UTF-8)', $filename)
	or die "Could not open file '$filename' $!";

while(my $word = <$fh>) {
	$wordCount++;
	chomp $word;
	processWord($word);
}	

(my $name, my $path, my $suffix) = fileparse($filename);

my $seqFile = '$path' . 'sequences.txt';
my $wordFile = '$path' . 'words.txt';

open(my $seqfh, '>', $seqFile)
	or die "Could not open file '$seqFile' $!";
	
open(my $wordfh, '>', $wordFile)
	or die "Could not open file '$wordFile' $!";


my $key;
foreach $key (sort(keys %sequences)) {
	print $seqfh "$key\n";
	print $wordfh "$sequences{$key}\n";
}

my $seqSize = keys %sequences;
my $dupeSize = keys %dupes;
print "Found $seqSize sequences in $wordCount words,\n";
print "with $dupeSize duplicate sequences found.\n";

close $seqfh;
close $wordfh;

sub processWord
{
	for(my $i = 0; $i + 4 <= length $_[0]; $i++) {
		my $testWord = substr $_[0], $i, 4;
		if(not exists($dupes{$testWord})) {
			if(not exists($sequences{$testWord})) {
				$sequences{$testWord} = $_[0];
			} else {
				$dupes{$testWord} = 1;
				delete $sequences{$testWord};
			}
		}
	}
}