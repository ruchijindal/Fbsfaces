/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 <body onload="fun()">
<script type="text/javascript">
//<![CDATA[
 function fun() {
   var datatableWidth = jQuery('div.ui-datatable > table').width();

   jQuery('div.ui-datatable > div.ui-datatable-header').width(datatableWidth - 22);
   jQuery('div.ui-datatable > div.ui-paginator').width(datatableWidth - 6);
};
//]]>
</script>
        </body>
