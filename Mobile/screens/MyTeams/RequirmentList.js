import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard , useWindowDimensions } from "react-native";
import { getLastLetter } from '../../utilies/index'
import { image, icons, color, FontSize, background } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { WebView } from 'react-native-webview';
import { QuillDeltaToHtmlConverter } from 'quill-delta-to-html';
import base64 from 'react-native-base64'
import { Buffer } from 'buffer'
import pako from 'pako';
import HTML from 'react-native-render-html';
function RequirementList(props) {
    
    const [expanded, setExpanded] = useState(false);

    const toggleExpand = () => {    
        
        setExpanded(!expanded);
    }

    const { content, id, insertTime, title } = props.requirement
    // Hàm giải mã nội dung từ Base64
    const decodeContent = (base64Content) => {
        if (!base64Content) return ''; // Kiểm tra nếu base64Content là undefined
         // Giải mã dữ liệu từ base64
    const compressedData = base64.decode(base64Content);

    // Chuyển đổi dữ liệu từ string sang Uint8Array
    const charData = compressedData.split('').map(x => x.charCodeAt(0));
    const binData = new Uint8Array(charData);

    // Giải nén dữ liệu
    const data = pako.inflate(binData, { to: 'string' });

    return data;
      }
    
      // Giải mã nội dung content
      const decodedContent = decodeContent(content);
         // Chuyển đổi delta sang HTML
     // Cấu hình (nếu cần)


// Tạo converter
const converter = new QuillDeltaToHtmlConverter(decodedContent, {});

// Chuyển đổi thành chuỗi HTML
const html = converter.convert();
const { width: contentWidth } = useWindowDimensions();

    return (<View style={{
        marginBottom: 5,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row',
        marginEnd: 10,
    }}>

        <View style={{
            flexDirection: 'column',
        }}>
            <TouchableOpacity onPress={toggleExpand}>
            <Text style={{
                color: 'black',
                fontSize: FontSize.h5,
                fontWeight: 500,
            }}>{title}</Text></TouchableOpacity>
            {expanded &&  <View style={{
                marginRight: 60,
              
            }}>
                 <HTML style={{
                    paddingEnd: 10,
                    color: color.inactive,
                    fontSize: FontSize.h5,
                    marginRight: 10,
                }} contentWidth={contentWidth} source={{ html: decodedContent }} />
           
            </View>}

        </View>

    </View>);
}
export default RequirementList
