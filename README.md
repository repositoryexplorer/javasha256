# javasha256
## Example implementation of sha256 and HKDF-SHA256 algorithms in Java language
Calculates SHA256 sum of a file or creates keying material based on HKDF-SHA256.

## Usage:
    java -jar javasha256.jar [options] [file]
        
        Options
         --a      algorithm to use, available values: \sha256\ or \hkdf\, default is \sha256\
         --k      file with key
         --o      optional, redirects result to a file
      
        When calculating key from hkdf, we have to provide key file. This file is JSON file with following fields: 
        key - input key, salt, length of the output key, info - optional, can contain application specific information
        Example key file:
        
                {
                    key: A0A0A0A0A0A0A0A,
                    salt: BFBFBFBFBFBFBF,
                    length: 32,
                    info: FEFEFEFEFEFE
                }

## Examples

java -jar javasha256.jar --a=sha256 file
java -jar javasha256.jar --a=hkdf --k=keyfile --o=outputfile
         
## License

MIT
**Free Software, Hell Yeah!**
