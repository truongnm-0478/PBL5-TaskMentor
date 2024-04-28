<template>
    <div class="editor">
        <a-input class="title" v-model:value="title" placeholder="Project name"/>
        <div class="ql-toolbar">
            <QuillEditor
                :toolbar="toolbarOptions"
                v-model="content"
                :options="editorOption"
                @update:content="handleContentUpdate"
            />
        </div>
        <div class="save-button">
            <a-float-button
                @click="save"
                shape="square"
                :style="{ right: '50px'}"
                type="primary"
            >
            </a-float-button>
        </div>
    </div>
</template>

<script>
import 'quill/dist/quill.snow.css'
import {ref, watch} from 'vue'
import {QuillEditor} from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import {QuillDeltaToHtmlConverter} from 'quill-delta-to-html'
import router from '@/router/index.js'
import pako from 'pako'
import projectApi from '@/repositories/projectApi.js'


export default {
    components: {QuillEditor},
    setup(props, context) {
        const content = ref('')
        const title = ref('')

        const editorOption = ref({
            // debug: 'info',
            placeholder: 'Type your post...',
            theme: 'snow',
            debug: false,
        })

        const toolbarOptions = [
            {'header': [1, 2, 3, 4, 5, 6, false]},
            'bold', 'italic', 'underline', 'strike', 'link',
            {'color': []}, {'background': []}, 'image',
            {'list': 'ordered'}, {'list': 'bullet'},
            {align: []},
        ]


        let delta = undefined

        watch(content, () => {
            delta = content.value.ops
        })


        const renderHTML = ref('')

        const handleContentUpdate = (newContent) => {
            const converter = new QuillDeltaToHtmlConverter(newContent.ops, {})
            renderHTML.value = converter.convert()
        }

        const save = () => {
            const id = router.currentRoute.value.query.id
            const compressedData = pako.deflate(renderHTML.value)
            const base64Data = btoa(String.fromCharCode.apply(null, new Uint8Array(compressedData)))
            const data = {
                teamId: id,
                title: title.value,
                contentBase64: base64Data
            }
            projectApi.addRequirement(data)
                .then(res => {
                    console.log("RES: ", res)
                    window.location.reload()
                })
                .catch(err => {
                    console.log("ERR: ", err)
                })
        }

        return {
            save,
            handleContentUpdate,
            renderHTML,
            toolbarOptions,
            content,
            editorOption,
            title
        }
    }
}


</script>
<style>
.editor {
    padding: 0 0 10px 0;
    background-color: var(--color-white);
    overflow: auto;
    border-radius: 10px;
    height: 100%;
}

.ql-toolbar {
    position: sticky;
    top: 0;
    background-color: var(--color-white);
    z-index: 2;
}

.ql-toolbar.ql-snow {
    border: none;
    border-bottom: 1px var(--color-gray-light) solid;

}
.ql-container.ql-snow {
    border: none;
}

.title {
    border: none;
    border-radius: 0px;
    font-size: 30px;
}

</style>