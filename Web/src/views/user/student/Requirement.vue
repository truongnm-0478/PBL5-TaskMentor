<template>
    <div class="editor">
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


export default {
    components: {QuillEditor},
    setup(props, context) {
        const content = ref('')

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
            console.log(renderHTML.value)
        }

        return {
            save,
            handleContentUpdate,
            renderHTML,
            toolbarOptions,
            content,
            editorOption,
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
    height: calc(100vh - 100px);
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

</style>