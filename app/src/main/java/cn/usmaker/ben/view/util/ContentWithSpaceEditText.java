package cn.usmaker.ben.view.util;

/**
 * Created by Administrator on 2016/9/6.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import cn.usmaker.base.R;
import cn.usmaker.ben.util.ToastUtils;


/**
 * Created by hzlinxuanxuan on 2015/12/22.
 * 该控件是支持输入时自带控件的，目前支持xml属性指定，也支持代码指定该输入卡所属的类型
 * 现在支持：电话、卡号、身份证号，或者无类型（正常输入）
 */
public class ContentWithSpaceEditText extends EditText {

    private int contentType;
    public static final int TYPE_PHONE = 0;
    public static final int TYPE_CARD = 1;
    public static final int TYPE_IDCARD = 2;

    public ContentWithSpaceEditText(Context context) {
        this(context, null);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ContentWithSpaceEditText, 0, 0);
        contentType = ta.getInt(R.styleable.ContentWithSpaceEditText_epaysdk_type, 0);
        ta.recycle();
        initType();
        setSingleLine();
        addTextChangedListener(watcher);
    }

    private void initType(){
        if (contentType == TYPE_PHONE) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        } else if (contentType == TYPE_CARD) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        } else if (contentType == TYPE_IDCARD) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        }
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
        initType();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null) {
                return;
            }
            //判断是否是在中间输入，需要重新计算
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格
            boolean isNeedSpace = false;
            if (!isMiddle && isSpace(s.length())) {
                isNeedSpace = true;
            }
            if (isMiddle || isNeedSpace) {
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");
                StringBuilder sb         = new StringBuilder();
                int           spaceCount = 0;
                for (int i = 0; i < newStr.length(); i++) {
                    sb.append(newStr.substring(i, i+1));
                    //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
                    if(isSpace(i + 2 + spaceCount)){
                        sb.append(" ");
                        spaceCount += 1;
                    }
                }
                removeTextChangedListener(watcher);
                setText(sb);
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if (!isMiddle || count > 1) {
                    setSelection(sb.length());
                } else if (isMiddle) {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if (isSpace(start - before + 1)) {
                            setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                            setSelection((start - before + 1) > sb.length() ? sb.length() : (start - before + 1));
                        }
                    }
                    //如果是增加
                    else {
                        if (isSpace(start - before + count)) {
                            setSelection((start + count - before + 1) < sb.length() ? (start + count - before + 1) : sb.length());
                        } else {
                            setSelection(start + count - before);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public String getTextWithoutSpace() {
        return super.getText().toString().replace(" ", "");
    }

    public boolean checkTextRight(){
        String text = getTextWithoutSpace();
        //这里做个简单的内容判断
        if (contentType == TYPE_PHONE) {
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showToast(getContext(), "手机号不能为空，请输入正确的手机号");
            } else if (text.length() < 11) {
                ToastUtils.showToast(getContext(), "手机号不足11位，请输入正确的手机号");
            } else {
                return true;
            }
        } else if (contentType == TYPE_CARD) {
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showToast(getContext(), "银行卡号不能为空，请输入正确的银行卡号");
            } else if (text.length() < 14) {
                ToastUtils.showToast(getContext(), "银行卡号位数不正确，请输入正确的银行卡号");
            } else {
                return true;
            }
        } else if (contentType == TYPE_IDCARD) {
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showToast(getContext(), "身份证号不能为空，请输入正确的身份证号");
            } else if (text.length() < 18) {
                ToastUtils.showToast(getContext(), "身份证号不正确，请输入正确的身份证号");
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isSpace(int length) {
        if (contentType == TYPE_PHONE) {
            return isSpacePhone(length);
        } else if (contentType == TYPE_CARD) {
            return isSpaceCard(length);
        } else if (contentType == TYPE_IDCARD) {
            return isSpaceIDCard(length);
        }
        return false;
    }


    private boolean isSpacePhone(int length) {
        if (length < 4) {
            return false;
        } else if (length == 4) {
            return true;
        } else return (length + 1) % 5 == 0;
    }

    private boolean isSpaceCard(int length) {
        return length % 5 == 0;
    }

    private boolean isSpaceIDCard(int length) {
        if (length <= 6) {
            return false;
        } else if (length == 7) {
            return true;
        } else return (length - 2) % 5 == 0;
    }

}
