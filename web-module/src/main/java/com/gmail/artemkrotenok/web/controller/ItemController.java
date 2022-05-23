package com.gmail.artemkrotenok.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import com.gmail.artemkrotenok.service.ItemService;
import com.gmail.artemkrotenok.service.model.ItemDTO;
import com.gmail.artemkrotenok.web.util.WhiteSourceUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(
            ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String getItemsPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        Long countItems = itemService.getCountItem();
        model.addAttribute("countItems", countItems);
        model.addAttribute("page", page);
        List<ItemDTO> itemsDTO = itemService.getItemsByPageSorted(page);
        model.addAttribute("items", itemsDTO);
        return "items";
    }

    @GetMapping("/{id}")
    public String getItemPage(@PathVariable Long id, Model model) {
        ItemDTO itemDTO = itemService.findById(id);
        if (itemDTO == null) {
            model.addAttribute("message", "Item not found for id='" + id + "'");
            model.addAttribute("redirect", "/items");
            return "message";
        }
        model.addAttribute("item", itemDTO);
        return "item";
    }

    @PostMapping("/delete")
    public String deleteItem(
            @RequestParam(name = "itemId") Long itemId,
            Model model) {
        itemService.deleteById(itemId);
        model.addAttribute("message", "Item was deleted successfully");
        model.addAttribute("redirect", "/items");
        return "message";
    }

    @PostMapping("/copy")
    public String copyItem(
            @RequestParam(name = "itemId") Long itemId,
            Model model) {
        itemService.copyById(itemId);
        model.addAttribute("message", "Item was copy successfully");
        model.addAttribute("redirect", "/items");
        return "message";
    }

    @GetMapping("/upload")
    public String getUploadItemsPage() {
        return "items_upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
            Model model) {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("redirect", "/items/upload");
            return "message";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            int countAddItems = itemService.addItemsAsJSON(path.toString());
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'"
                            + " and add " + countAddItems + " items");
            model.addAttribute("redirect", "/items/upload");
        } catch (IOException e) {
            model.addAttribute("message", "Error upload file");
            model.addAttribute("redirect", "/items");
        }
        return "message";
    }

    @PostMapping("/uploadIsSubDirectory")
    public String singleFileUploadIsSubDirectory(@RequestParam("file") MultipartFile file,
                                   Model model) throws Exception {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("redirect", "/items/upload");
            return "message";
        }

        try {
            if(!WhiteSourceUtil.isSubDirectory(file.getOriginalFilename()))
                throw new Exception("The filename, directory name, or volume label syntax is incorrect");

            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            int countAddItems = itemService.addItemsAsJSON(path.toString());
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'"
                            + " and add " + countAddItems + " items");
            model.addAttribute("redirect", "/items/upload");
        } catch (IOException e) {
            model.addAttribute("message", "Error upload file");
            model.addAttribute("redirect", "/items");
        }
        return "message";
    }

    @PostMapping("/uploadSanitizer")
    public String singleFileUploadSanitizer(@RequestParam("file") MultipartFile file,
                                            Model model) throws Exception {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("redirect", "/items/upload");
            return "message";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FilenameUtils.getName(file.getOriginalFilename()));
            Files.write(path, bytes);
            int countAddItems = itemService.addItemsAsJSON(path.toString());
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'"
                            + " and add " + countAddItems + " items");
            model.addAttribute("redirect", "/items/upload");
        } catch (IOException e) {
            model.addAttribute("message", "Error upload file");
            model.addAttribute("redirect", "/items");
        }
        return "message";
    }

    @PostMapping("/uploadCheckFileName")
    public String singleFileUploadCheckFileName(@RequestParam("file") MultipartFile file,
                                   Model model) throws Exception {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("redirect", "/items/upload");
            return "message";
        }

        try {
            byte[] bytes = file.getBytes();

            if(!file.getOriginalFilename().equalsIgnoreCase("item.txt"))
                throw new Exception("The filename is incorrect");

            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            int countAddItems = itemService.addItemsAsJSON(path.toString());
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'"
                            + " and add " + countAddItems + " items");
            model.addAttribute("redirect", "/items/upload");
        } catch (IOException e) {
            model.addAttribute("message", "Error upload file");
            model.addAttribute("redirect", "/items");
        }
        return "message";
    }

    @PostMapping("/uploadCheckPattern")
    public String singleFileUploadCheckPattern(@RequestParam("file") MultipartFile file,
                                                Model model) throws Exception {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("redirect", "/items/upload");
            return "message";
        }

        try {
            byte[] bytes = file.getBytes();

            Pattern p = Pattern.compile("^[A-Za-z0-9]+\\.txt$");

            if(!p.matcher(file.getOriginalFilename()).matches())
                throw new Exception("The filename is incorrect");

            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            int countAddItems = itemService.addItemsAsJSON(path.toString());
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'"
                            + " and add " + countAddItems + " items");
            model.addAttribute("redirect", "/items/upload");
        } catch (IOException e) {
            model.addAttribute("message", "Error upload file");
            model.addAttribute("redirect", "/items");
        }
        return "message";
    }

    @PostMapping("/uploadCheckUnsafePattern")
    public String singleFileUploadCheckUnsafePattern(@RequestParam("file") MultipartFile file,
                                               Model model) throws Exception {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("redirect", "/items/upload");
            return "message";
        }

        try {
            byte[] bytes = file.getBytes();

            Pattern p = Pattern.compile("^[a-zA-Z0-9.\\/]+$");

            if(!p.matcher(file.getOriginalFilename()).matches())
                throw new Exception("The filename is incorrect");

            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            int countAddItems = itemService.addItemsAsJSON(path.toString());
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'"
                            + " and add " + countAddItems + " items");
            model.addAttribute("redirect", "/items/upload");
        } catch (IOException e) {
            model.addAttribute("message", "Error upload file");
            model.addAttribute("redirect", "/items");
        }
        return "message";
    }

}
