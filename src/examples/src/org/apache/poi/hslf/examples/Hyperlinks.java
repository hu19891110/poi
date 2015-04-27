/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hslf.examples;

import org.apache.poi.hslf.usermodel.*;

import java.io.FileInputStream;

/**
 * Demonstrates how to read hyperlinks from  a presentation
 *
 * @author Yegor Kozlov
 */
public final class Hyperlinks {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            FileInputStream is = new FileInputStream(args[i]);
            HSLFSlideShow ppt = new HSLFSlideShow(is);
            is.close();

            HSLFSlide[] slide = ppt.getSlides();
            for (int j = 0; j < slide.length; j++) {
                System.out.println("slide " + slide[j].getSlideNumber());

                //read hyperlinks from the slide's text runs
                System.out.println("reading hyperlinks from the text runs");
                HSLFTextParagraph[] txt = slide[j].getTextParagraphs();
                for (int k = 0; k < txt.length; k++) {
                    String text = txt[k].getRawText();
                    HSLFHyperlink[] links = txt[k].getHyperlinks();
                    if(links != null) for (int l = 0; l < links.length; l++) {
                        HSLFHyperlink link = links[l];
                        String title = link.getTitle();
                        String address = link.getAddress();
                        System.out.println("  " + title);
                        System.out.println("  " + address);
                        String substring = text.substring(link.getStartIndex(), link.getEndIndex()-1);//in ppt end index is inclusive
                        System.out.println("  " + substring);
                    }
                }

                //in PowerPoint you can assign a hyperlink to a shape without text,
                //for example to a Line object. The code below demonstrates how to
                //read such hyperlinks
                System.out.println("  reading hyperlinks from the slide's shapes");
                HSLFShape[] sh = slide[j].getShapes();
                for (int k = 0; k < sh.length; k++) {
                    HSLFHyperlink link = sh[k].getHyperlink();
                    if(link != null)  {
                        String title = link.getTitle();
                        String address = link.getAddress();
                        System.out.println("  " + title);
                        System.out.println("  " + address);
                    }
                }
            }

        }

   }
}
