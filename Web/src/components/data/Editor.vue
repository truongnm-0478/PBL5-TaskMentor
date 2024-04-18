<template>
    <div class="editor">
        <div class="row d-flex justify-content-center">
            <div  class="col-lg-7">
                <QuillEditor
                    :toolbar="toolbarOptions"
                    v-model="content"
                    :options="editorOption"
                    @update:content="handleContentUpdate"
                    :modules="modules"
                />
            </div>
            <div class="col-lg-7 d-flex justify-content-end my-4">
                <SiteButton @click="save" style="width:100px" title="POST" class="me-2"/>
            </div>
        </div>

    </div>
</template>

<script>
import 'quill/dist/quill.snow.css'
import { ref, watch } from 'vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { QuillDeltaToHtmlConverter } from 'quill-delta-to-html'
import ImageUploader from 'quill-image-uploader'


export default {
    components: { QuillEditor },
    setup(props, context) {
        const content = ref('')

        const editorOption = ref({
            // debug: 'info',
            placeholder: 'Type your post...',
            theme: 'snow',
            debug: false,
        })

        const toolbarOptions = [
            { 'header': [1, 2, 3, 4, 5, 6, false] },
            'bold', 'italic', 'underline', 'strike', 'link',
            { 'color': [] }, { 'background': [] }, 'image',
            { 'list': 'ordered'}, { 'list': 'bullet' },
            { align: [] },

        ]

        const modules = {
            name: 'imageUploader',
            module: ImageUploader,
            options: {
                upload: file => {
                    return new Promise((resolve, reject) => {
                        const formData = new FormData()
                        formData.append('file', file)

                        blogApi.uploadImage(formData)
                            .then(res => {
                                resolve(res.fileUrl)
                            })
                            .catch(err => {
                                reject('Upload failed')
                                console.error('Error:', err)
                            })
                    })
                }
            }
        }


        let delta = undefined

        watch(content, () => {
            delta = content.value.ops
        })


        const renderHTML = ref('')

        const handleContentUpdate = (newContent) => {
            var deltaOps = newContent
            var converter = new QuillDeltaToHtmlConverter(deltaOps.ops, {})
            renderHTML.value = converter.convert()
        }

        const save = () => {
            context.emit('save-blog', renderHTML.value)
        }

        return {
            save,
            handleContentUpdate,
            renderHTML,
            toolbarOptions,
            content,
            editorOption,
            modules,
        }
    }
}


</script>
<style scoped>
* {
    box-sizing: border-box;
}

</style>

<style >
.ql-toolbar {
    position: sticky;
    top: 84px;
    background-color: var(--color-white);
    z-index: 2;
}

</style>